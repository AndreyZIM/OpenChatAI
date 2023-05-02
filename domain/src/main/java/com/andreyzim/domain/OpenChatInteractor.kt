package com.andreyzim.domain


interface OpenChatInteractor {

    suspend fun sendMessage(body: String): RequestResult
    suspend fun clearMessages()

    class Base(
        private val repository: OpenChatRepository,
        private val handleRequest: HandleDomainRequest
    ) : OpenChatInteractor {
        override suspend fun sendMessage(body: String): RequestResult = handleRequest.handle {
            repository.sendMessage(body)
        }

        override suspend fun clearMessages() {
            repository.clearMessages()
        }
    }
}
