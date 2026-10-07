package doonv.jettop.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExpandedDockedSearchBarWithGap
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarScrollBehavior
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSearchBarWithGapState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import doonv.jettop.R
import doonv.jettop.data.InboxMessage
import doonv.jettop.ui.theme.Symbols
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MessagesScreen(
    modifier: Modifier = Modifier,
    vm: MessagesViewModel,
    onRefresh: () -> Unit,
    onMessageClick: (InboxMessage) -> Unit
) {
    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        topBar = { MessagesTopBar(scrollBehavior) },
        floatingActionButton = { MessagesScreenFab() }
    ) { innerPadding ->
        val lazyPagingItems = vm.messages.collectAsLazyPagingItems()
        val pullToRefreshState = rememberPullToRefreshState()
        val isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading &&
                lazyPagingItems.itemCount > 0

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                lazyPagingItems.refresh()
                onRefresh()
            },
            state = pullToRefreshState,
            modifier = Modifier.padding(innerPadding),
            indicator = {
                PullToRefreshDefaults.LoadingIndicator(
                    isRefreshing = isRefreshing,
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = pullToRefreshState
                )
            }
        ) {
            when (val s = lazyPagingItems.loadState.refresh) {
                is LoadState.Loading if lazyPagingItems.itemCount == 0 -> Box(
                    Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { LoadingIndicator() }

                is LoadState.Error -> Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Column(Modifier.fillMaxWidth(0.8f)) {
                        Text(
                            stringResource(R.string.error_message),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Text(s.error.localizedMessage ?: s.error.message ?:s.error.toString(), color = MaterialTheme.colorScheme.error)
                        Button(
                            onClick = { lazyPagingItems.retry() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(stringResource(R.string.retry))
                        }
                    }
                }

                else -> MessagesList(
                    lazyPagingItems,
                    vm,
                    innerPadding,
                    scrollBehavior,
                    onMessageClick
                )
            }
        }
    }
}

@Composable
private fun MessagesList(
    lazyPagingItems: LazyPagingItems<InboxMessage>,
    vm: MessagesViewModel,
    innerPadding: PaddingValues,
    scrollBehavior: SearchBarScrollBehavior,
    onMessageClick: (InboxMessage) -> Unit,
) {
    val defaultLayoutDirection = LocalLayoutDirection.current
    val locallyRead by vm.locallyRead.collectAsStateWithLifecycle()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        items(
            lazyPagingItems.itemCount,
            key = lazyPagingItems.itemKey { it.messageId }) { index ->
            val message = lazyPagingItems[index]!!
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                val rounded = MaterialTheme.shapes.large
                val base = MaterialTheme.shapes.extraSmall
                val count = lazyPagingItems.itemCount
                val itemShape = RoundedCornerShape(
                    topStart = if (index == 0) rounded.topStart else base.topStart,
                    topEnd = if (index == 0) rounded.topEnd else base.topEnd,
                    bottomEnd = if (index == count - 1) rounded.bottomEnd else base.bottomEnd,
                    bottomStart = if (index == count - 1) rounded.bottomStart else base.bottomStart,
                )
                val msg = if (message.messageId in locallyRead) message.copy(hasRead = 1) else message

                MessageRow(
                    msg,
                    onClick = { onMessageClick(message) },
                    itemShape,
                    defaultLayoutDirection
                )
            }
        }
        item {
            Spacer(Modifier.size(8.dp))
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MessagesScreenFab() {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        tooltip = {
            PlainTooltip {
                Text(stringResource(R.string.compose))
            }
        },
        state = rememberTooltipState(),
    ) {
        FloatingActionButton(onClick = { /* do something */ }) {
            Icon(Symbols.Filled.Edit24, stringResource(R.string.compose))
        }
    }
}

@Composable
fun MessageRow(
    message: InboxMessage,
    onClick: () -> Unit,
    shape: Shape,
    defaultLayoutDirection: LayoutDirection
) {
    val textColor =
        if (message.isRead) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
    val context = LocalContext.current
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(Modifier.padding(8.dp, 4.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    message.senderFullName ?: stringResource(R.string.message_unknown_sender),
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                CompositionLocalProvider(LocalLayoutDirection provides defaultLayoutDirection) {
                    Text(
                        message.dateLabel(context),
                        style = MaterialTheme.typography.labelMedium,
                        color = textColor,
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    message.subject ?: stringResource(R.string.message_no_subject),
                    modifier = Modifier.weight(1f, fill = false),
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor
                )
                if (message.hasAttachments) {
                    Icon(
                        Symbols.Outlined.AttachFile20,
                        contentDescription = stringResource(R.string.has_attachments),
                        modifier = Modifier.size(14.dp),
                        tint = textColor
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MessagesTopBar(
    scrollBehavior: SearchBarScrollBehavior
) {
    val scope = rememberCoroutineScope()
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarWithGapState()
    val appBarWithSearchColors = SearchBarDefaults.appBarWithSearchColors(
        appBarContainerColor = Color.Transparent,
        scrolledAppBarContainerColor = Color.Transparent,
        scrolledSearchBarContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    )

    val inputField = @Composable {
        val style = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        SearchBarDefaults.InputField(
            textFieldState = textFieldState,
            searchBarState = searchBarState,
            colors = appBarWithSearchColors.searchBarColors.inputFieldColors,
            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
            textStyle = style,
            placeholder = {
                // workaround for placeholder centering not working
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.search),
                        modifier = Modifier.clearAndSetSemantics {},
                    )
                }
            })
    }
    AppBarWithSearch(
        scrollBehavior = scrollBehavior,
        state = searchBarState,
        colors = appBarWithSearchColors,
        inputField = inputField,
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    Symbols.Outlined.Folder24,
                    contentDescription = stringResource(R.string.folder)
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    Symbols.Outlined.FilterList24,
                    contentDescription = stringResource(R.string.filter)
                )
            }
        },
    )
    ExpandedDockedSearchBarWithGap(state = searchBarState, inputField = inputField) {
//                LazyColumn {
//                    items(results) { result ->
//                        ListItem(
//                            headlineContent = { Text(result.subject ?: "") },
//                            supportingContent = { Text(result.senderLastName ?: "") },
//                            modifier = Modifier.clickable {
//                                textFieldState.setTextAndPlaceCursorAtEnd(result.subject ?: "")
//                                scope.launch { searchBarState.animateToCollapsed() }
//                            },
//                        )
//                    }
//                }
    }

}
