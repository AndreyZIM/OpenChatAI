package com.andreyzim.appcompose.ui

import com.andreyzim.domain.MessageDomain
import javax.inject.Inject

class MessageToUIMapper @Inject constructor() : MessageDomain.Mapper<MessageUI> {
    override fun map(message: String, created: Long, received: Boolean): MessageUI = MessageUI(
        message,
        created,
        if (received) MessageType.RECEIVED else MessageType.SENT
    )
}