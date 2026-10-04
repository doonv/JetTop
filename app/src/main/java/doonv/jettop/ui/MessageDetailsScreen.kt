package doonv.jettop.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import doonv.jettop.R
import doonv.jettop.data.MessageDetails
import doonv.jettop.ui.theme.Symbols

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MessageDetailsScreen(
    modifier: Modifier = Modifier,
    vm: MessageDetailsViewModel,
    onBack: () -> Unit,
    messageId: String
) {
    val state by vm.state.collectAsState()
    Scaffold(
        modifier,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Symbols.Outlined.ArrowBack24,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Symbols.Outlined.Delete24,
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Symbols.Outlined.Reply24,
                            contentDescription = stringResource(R.string.reply)
                        )
                    }
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(
                            Symbols.Outlined.Forward24,
                            contentDescription = stringResource(R.string.forward)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when (val s = state) {
            is MessageDetailsUiState.Loading -> Box(
                Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { LoadingIndicator() }

            is MessageDetailsUiState.Error -> Column(
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
                    Text(s.message, color = MaterialTheme.colorScheme.error)
                    Button(
                        onClick = { vm.load(messageId) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(stringResource(R.string.retry))
                    }
                }
            }

            is MessageDetailsUiState.Success -> MessageDetailsSuccess(
                innerPadding,
                s.message,
                vm
            )
        }
    }
}

@Composable
private fun MessageDetailsSuccess(
    innerPadding: PaddingValues,
    messageDetails: MessageDetails,
    vm: MessageDetailsViewModel
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val defaultLayoutDirection = LocalLayoutDirection.current
    Column(
        Modifier
            .verticalScroll(scrollState)
            .padding(innerPadding)
            .padding(8.dp),
        Arrangement.spacedBy(8.dp)
    ) {
        val message = messageDetails.messageData
        Text(
            message.subject ?: stringResource(R.string.message_no_subject),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge.copy(textDirection = TextDirection.ContentOrRtl),
        )
        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
        ) {
            Column(Modifier.padding(8.dp), Arrangement.spacedBy(16.dp)) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Column {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                message.senderFullName
                                    ?: stringResource(R.string.message_unknown_sender),
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            CompositionLocalProvider(LocalLayoutDirection provides defaultLayoutDirection) {
                                Text(
                                    message.dateLabel(context),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Text(
                            messageDetails.roles.orEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                val body = remember(message.messageContent) {
                    val raw = message.messageContent.orEmpty().trim()
                    runCatching { AnnotatedString.fromHtml(raw) }.getOrElse { AnnotatedString(raw) }
                }
                Text(
                    body,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.ContentOrRtl)
                )

                if (message.hasAttachments) {
                    HorizontalDivider()
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    message.filesList.forEach { file ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                Symbols.Filled.Download24,
                                contentDescription = stringResource(R.string.download)
                            )
                            Text(
                                buildAnnotatedString {
                                    append(file.fileName)
                                    append(" ")
                                    withStyle(
                                        SpanStyle(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 12.sp
                                        )
                                    ) {
                                        append(file.fileSize.toString())
                                    }
                                },
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { vm.downloadAttachment(file) },
                                style = MaterialTheme.typography.bodyLarge.copy(textDirection = TextDirection.ContentOrLtr)
                            )
                        }
                    }
                }

            }
        }
    }
}
