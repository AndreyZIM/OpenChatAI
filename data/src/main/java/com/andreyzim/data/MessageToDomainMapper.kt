package com.andreyzim.data

import com.andreyzim.domain.MessageDomain
import javax.inject.Inject

class MessageToDomainMapper @Inject constructor() : MessageData.Mapper<MessageDomain> {
    override fun map(message: String, created: Long, received: Boolean): MessageDomain =
        MessageDomain(message, created, received)
}