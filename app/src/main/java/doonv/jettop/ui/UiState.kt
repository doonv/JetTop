package doonv.jettop.ui

import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

enum class LoadTrigger {
    Automatic,
    Refresh
}

sealed interface UiState<out T> {
    val loadTrigger: LoadTrigger? get() = null
    val isRefreshing: Boolean get() = loadTrigger == LoadTrigger.Refresh

    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
        override val loadTrigger: LoadTrigger? = null
    ) : UiState<T>

    data class Error(val error: Throwable) : UiState<Nothing>
}

fun <T> UiState<T>.startLoading(trigger: LoadTrigger): UiState<T> = when (this) {
    is UiState.Success -> copy(loadTrigger = trigger)
    else -> UiState.Loading
}

fun <T, R> UiState<T>.map(transform: (T) -> R) = when (this) {
    is UiState.Success -> UiState.Success(data = transform(data), loadTrigger = loadTrigger)
    is UiState.Error -> UiState.Error(error)
    UiState.Loading -> UiState.Loading
}

suspend fun <T> UiState<T>.load(
    request: suspend () -> T,
): UiState<T> {
    return try {
        UiState.Success(request())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        if (e is IOException && this is UiState.Success) {
            copy(loadTrigger = null)
        } else {
            UiState.Error(e)
        }
    }
}


fun <A, B> mergeStates(first: UiState<A>, second: UiState<B>): UiState<Pair<A, B>> = when {
    first is UiState.Success && second is UiState.Success -> UiState.Success(
        data = first.data to second.data,
        loadTrigger = if (first.isRefreshing || second.isRefreshing) LoadTrigger.Refresh
        else first.loadTrigger ?: second.loadTrigger
    )
    first is UiState.Error -> first
    second is UiState.Error -> second
    else -> UiState.Loading
}

fun <A, B> mergeStates(
    first: UiState<A>,
    firstRetry: Refreshable,
    second: UiState<B>,
    secondRetry: Refreshable,
): Pair<UiState<Pair<A, B>>, Refreshable> {
    val refreshBoth = Refreshable {
        firstRetry.refresh()
        secondRetry.refresh()
    }
    val retry = when {
        first is UiState.Error -> firstRetry
        second is UiState.Error -> secondRetry
        else -> refreshBoth
    }
    return mergeStates(first, second) to retry
}
