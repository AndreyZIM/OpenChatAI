package com.andreyzim.appcompose.ui

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class MessageUI(
    //TODO do private
    val body: String,
    val created: Long,
    val messageType: MessageType
) {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(body, created, messageType)

    interface Mapper<T> {
        fun map(body: String, created: Long, messageType: MessageType): T
    }

    @Composable
    fun toComposable(modifier: Modifier, onClick: (ClipData) -> Unit) {
        if (messageType == MessageType.RECEIVED) {
            ReceivedMessage(body = body, showAvatar = true, onClick = onClick)
        } else {
            SentMessage(body = body, modifier = modifier, onClick = onClick)
        }
    }
}
