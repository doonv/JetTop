package doonv.jettop.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

class MessagesPagingSource(
    private val api: WebtopApi,
    private val cookie: String,
    private val labelId: Int,
    private val query: String,
    private val logout: () -> Unit
) : PagingSource<Int, InboxMessage>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, InboxMessage> {
        try {
            val nextPageNumber = params.key ?: 1
            val response =
                api.getMessagesInbox(
                    MessagesInboxRequest(
                        labelId = labelId,
                        pageId = nextPageNumber,
                        searchQuery = query
                    ),
                    cookie
                )
            if (!response.status) return LoadResult.Error(
                IOException(response.message ?: response.errorDescription ?: "status=false")
            )
            return LoadResult.Page(
                data = response.data,
                prevKey = if (nextPageNumber <= 1) null else nextPageNumber - 1,
                nextKey = if (response.data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                logout()
                // Return an empty page until the login page loads
                return LoadResult.Page(emptyList(), null, null)
            }
            // TODO: do proper error handling
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, InboxMessage>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
}