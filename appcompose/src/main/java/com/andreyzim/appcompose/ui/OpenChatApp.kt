@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreyzim.appcompose.R
import com.example.compose.OpenChatAITheme
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

    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()
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
                        enabled = !(dialogState is DialogState.Loading || uiState is UiState.Waiting)
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
                    onValueChange = { textedMessage = it },
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    maxLines = 6,
                    isError = dialogState is DialogState.Error,
                    placeholder = { Text("Type here...") }
                )
                IconButton(
                    onClick = {
                        viewModel.sendMessage(textedMessage.text)
                        textedMessage = TextFieldValue("")
                    }, modifier = Modifier
                        .padding(0.dp, 8.dp, 8.dp, 8.dp)
                        .align(Alignment.Bottom),
                    enabled = !(dialogState is DialogState.Loading || uiState is UiState.Waiting)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_send_24),
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        if (dialogState is DialogState.Loading)
            Box(modifier = modifier.padding(it)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        else {
            if ((dialogState as DialogState.Success).messageList.isEmpty()) {
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

                    items((dialogState as DialogState.Success).messageList) { message ->
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
    onClick: (ClipData) -> Unit
) {
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
            Text(
                text = body,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

@Composable
fun ReceivedMessage(
    modifier: Modifier = Modifier,
    body: String,
    showAvatar: Boolean,
    onClick: (ClipData) -> Unit
) {
    Row(modifier = modifier) {
        if (showAvatar) {
            Card(
                modifier = Modifier
                    .padding(16.dp, 0.dp, 8.dp, 0.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(42.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.openai_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .padding(6.dp)
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
            Text(
                text = body,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
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