package com.andreyzim.data.cache

import com.andreyzim.data.MessageData
import javax.inject.Inject

class DataToCacheMapper @Inject constructor() : MessageData.Mapper<MessageCache> {
    override fun map(message: String, created: Long, received: Boolean): MessageCache =
        MessageCache(0, message, received, created)
}