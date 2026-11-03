package doonv.jettop.ui

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Success<T>(
        val data: T,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false,
        val refreshError: Error? = null,
    ) : UiState<T>

    data class Error(val message: String) : UiState<Nothing>
}

fun <T> UiState<T>.startRefreshing(): UiState<T> = when (this) {
    is UiState.Success -> copy(isRefreshing = true, isOffline = false, refreshError = null)
    else -> UiState.Loading
}

fun <T> UiState<T>.withError(message: String, isOffline: Boolean = false): UiState<T> =
    when (this) {
        is UiState.Success -> copy(
            isRefreshing = false,
            isOffline = isOffline,
            refreshError = UiState.Error(message),
        )
        else -> UiState.Error(message)
    }

fun <A, B> mergeStates(first: UiState<A>, second: UiState<B>): UiState<Pair<A, B>> = when {
    first is UiState.Success && second is UiState.Success -> UiState.Success(
        data = first.data to second.data,
        isRefreshing = first.isRefreshing || second.isRefreshing,
        isOffline = first.isOffline || second.isOffline,
        refreshError = first.refreshError ?: second.refreshError,
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
        first is UiState.Success && second is UiState.Success -> when {
            first.refreshError != null -> firstRetry
            second.refreshError != null -> secondRetry
            else -> refreshBoth
        }
        else -> refreshBoth
    }
    return mergeStates(first, second) to retry
}
