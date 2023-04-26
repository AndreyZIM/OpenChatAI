package com.andreyzim.data

import android.util.Log
import com.andreyzim.data.cache.MessageCacheDataSource
import com.andreyzim.data.cloud.MessageCloudDataSource
import com.andreyzim.data.cloud.RequestMessage
import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.MessageResult
import com.andreyzim.domain.OpenChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BaseOpenChatRepository(
    private val cacheDataSource: MessageCacheDataSource,
    private val cloudDataSource: MessageCloudDataSource,
    private val mapper: MessageData.Mapper<MessageDomain>
) : OpenChatRepository {

    override var data: Flow<List<MessageDomain>> = cacheDataSource.allMessages()
        .map { list ->
//            MessageResult.Success(list.map { messageData -> messageData.map(mapper) })
            list.map {messageData -> messageData.map(mapper)}
        }

    override suspend fun sendMessage(body: String) {
        cacheDataSource.saveMessage(MessageData(body, System.currentTimeMillis(), false))
        Log.i("SEND", "repository body: ${cacheDataSource.allMessages().first()}")
        val list = cacheDataSource.allMessages().first().map {
            RequestMessage(
                if (it.received) "assistant" else "user",
                it.message
            )
        }.reversed()
        val receivedMessage = cloudDataSource.sendMessage(list)
        cacheDataSource.saveMessage(receivedMessage)
    }

    override suspend fun clearMessages() {
        cacheDataSource.clearMessages()
    }
}