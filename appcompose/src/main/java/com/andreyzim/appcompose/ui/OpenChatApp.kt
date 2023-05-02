@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreyzim.appcompose.R
import com.example.compose.OpenChatAITheme
import java.text.SimpleDateFormat
import kotlin.math.ln

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun OpenChatApp(
    modifier: Modifier = Modifier,
    viewModel: OpenChatViewModel = hiltViewModel(),
    clipboardManager: ClipboardManager,
    // TODO network monitor
) {
    var textedMessage by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val messageListStateState by viewModel.messageListStateState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("OpenChatAI") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                ),
                actions = {
                    IconButton(
                        onClick = { viewModel.clearMessages() },
                        enabled = !(messageListStateState is MessageListStateState.Loading || uiState is UiState.Waiting)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_clear_all_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
            ) {
                TextField(
                    value = textedMessage,
                    onValueChange = {
                        if (uiState is UiState.TypingError)
                            viewModel.resetError()
                        textedMessage = it
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    maxLines = 6,
                    isError = uiState is UiState.TypingError,
                    placeholder = { Text("Type here...") },
                    supportingText = {
                        if (uiState is UiState.TypingError)
                            Text(text = (uiState as UiState.TypingError).message)
                    }
                )
                IconButton(
                    onClick = {
                        viewModel.sendMessage(textedMessage.text)
                        textedMessage = TextFieldValue("")
                    }, modifier = Modifier
                        .padding(0.dp, 8.dp, 8.dp, 16.dp)
                        .align(Alignment.Bottom),
                    enabled = !(messageListStateState is MessageListStateState.Loading || uiState is UiState.Waiting)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_send_24),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        if (messageListStateState is MessageListStateState.Loading)
            Box(modifier = modifier.padding(it)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        else {
            if ((messageListStateState as MessageListStateState.Success).messageList.isEmpty()) {
                Box(modifier = modifier.padding(it)) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Icon(
                            painter = painterResource(id = R.drawable.openai_icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(0.dp, 0.dp, 0.dp, 8.dp)
                                .size(42.dp)
                        )
                        Text(
                            text = "Conversation is empty.",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            } else Column(modifier = modifier.padding(it)) {
                if (uiState is UiState.Waiting)
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.weight(1f))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = true
                ) {
                    item {
                        Spacer(Modifier.size(8.dp))
                    }
                    items((messageListStateState as MessageListStateState.Success).messageList) { message ->
                        message.toComposable(
                            modifier = Modifier
                                .align(Alignment.End)
                                .animateItemPlacement()
                        ) { clip ->
                            clipboardManager.setPrimaryClip(clip)
                        }
                    }
                    item {
                        Spacer(Modifier.size(8.dp))
                    }

                }
            }
        }
    }
}

fun ColorScheme.surfaceColorAtElevation(
    elevation: Dp,
): Color {
    if (elevation == 0.dp) return surface
    val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
    return surfaceTint.copy(alpha = alpha).compositeOver(surface)
}

@Composable
fun SentMessage(
    modifier: Modifier = Modifier,
    body: String,
    onClick: (ClipData) -> Unit,
    created: Long,
    isError: Boolean = false
) {
    val dateFormat = SimpleDateFormat("kk:mm")
    val time = dateFormat.format(created)
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))
        Card(
            modifier = Modifier
                .wrapContentWidth()
                .padding(66.dp, 0.dp, 16.dp, 0.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                val clip = ClipData.newPlainText("Clipped Text", body)
                onClick(clip)
            }
        ) {
            Column(
                Modifier
                    .padding(16.dp, 16.dp, 16.dp, 6.dp)
            ) {
                Text(
                    text = body,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = time,
                    modifier = Modifier.align(Alignment.End),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.labelSmall
                )
                if (isError)
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
            }
        }
    }

}

@ExperimentalMaterial3Api
@Composable
fun ReceivedMessage(
    modifier: Modifier = Modifier,
    body: String,
    showAvatar: Boolean,
    created: Long,
    onClick: (ClipData) -> Unit
) {
    val dateFormat = SimpleDateFormat("kk:mm")
    val time = dateFormat.format(created)
    Row(modifier = modifier) {
        if (showAvatar) {
            Card(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 8.dp, 0.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(42.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.openai_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .padding(6.dp),
                    tint = MaterialTheme.colorScheme.onSurface

                )
            }
        }
        Card(
            modifier = modifier
                .wrapContentWidth()
                .padding(
                    if (showAvatar) 0.dp else 66.dp,
                    0.dp,
                    66.dp,
                    0.dp
                ),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                val clip = ClipData.newPlainText("Clipped Text", body)
                onClick(clip)
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 6.dp)
            ) {
                Text(
                    text = body,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 2.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = time,
                    modifier = Modifier.align(Alignment.End),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
fun MessageCardPreview() {
    OpenChatAITheme() {
//        ReceivedMessage(body = "Hello!", showAvatar = true)
    }
}