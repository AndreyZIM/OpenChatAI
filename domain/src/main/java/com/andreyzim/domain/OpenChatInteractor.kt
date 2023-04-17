package com.andreyzim.domain

class OpenChatInteractor(
    private val repository: OpenChatRepository,
    private val handleRequest: HandleRequest
) {

//    suspend fun sendMessage(body: String) : MessageResult = handleRequest.handle {
//        repository.sendMessage(body)
//    }
}