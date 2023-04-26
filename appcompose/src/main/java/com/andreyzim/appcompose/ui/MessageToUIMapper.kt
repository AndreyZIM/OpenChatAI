package com.andreyzim.appcompose.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import com.andreyzim.domain.MessageDomain
import javax.inject.Inject

@ExperimentalMaterial3Api
class MessageToUIMapper @Inject constructor() : MessageDomain.Mapper<MessageUI> {
    override fun map(message: String, created: Long, received: Boolean): MessageUI = MessageUI(
        message,
        created,
        if (received) MessageType.RECEIVED else MessageType.SENT
    )
}