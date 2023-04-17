package com.andreyzim.data.cache

import com.andreyzim.data.MessageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class MessageCacheDataSource @Inject constructor(
    private val dao: MessageDao,
    private val dataToCache: DataToCacheMapper
) {

    private val mutex = Mutex()

    fun allMessages(): Flow<List<MessageData>> =
        dao.allMessages()
        .map {
            it.map { messageCache ->
                MessageData(
                    messageCache.message,
                    messageCache.created,
                    messageCache.received
                )
            }
        }

    suspend fun saveMessage(messageData: MessageData) {
        dao.insert(messageData.map(dataToCache))
    }

    suspend fun clearMessages() {
        dao.clear()
    }
}