package com.andreyzim.domain

import kotlinx.coroutines.flow.Flow

interface OpenChatRepository {

//    var data: Flow<MessageResult>
    var data: Flow<List<MessageDomain>>

    suspend fun sendMessage(body: String)

    suspend fun clearMessages()
}