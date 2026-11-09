package doonv.jettop.ui

import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

sealed interface UiState<out T> {
    val isRefreshing: Boolean get() = (this as? Success)?.isRefreshing == true

    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
        override val isRefreshing: Boolean = false
    ) : UiState<T>

    data class Error(val error: Throwable) : UiState<Nothing>
}

fun <T> UiState<T>.startRefreshing(): UiState<T> = when (this) {
    is UiState.Success -> copy(isRefreshing = true)
    else -> UiState.Loading
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
            copy(isRefreshing = false)
        } else {
            UiState.Error(e)
        }
    }
}


fun <A, B> mergeStates(first: UiState<A>, second: UiState<B>): UiState<Pair<A, B>> = when {
    first is UiState.Success && second is UiState.Success -> UiState.Success(
        data = first.data to second.data,
        isRefreshing = first.isRefreshing || second.isRefreshing
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
