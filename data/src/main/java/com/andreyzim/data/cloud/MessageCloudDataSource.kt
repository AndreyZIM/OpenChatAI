package com.andreyzim.data.cloud

import com.andreyzim.data.MessageData
import javax.inject.Inject

class MessageCloudDataSource @Inject constructor(
    private val service: OpenChatService
) {

    suspend fun sendMessage(history: List<RequestMessage>): MessageData {
        val result = service.send(
            RequestBody(
                messages = history
            )
        )
        return result.toMessageData()
    }
}