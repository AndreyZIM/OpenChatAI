package com.andreyzim.domain

class HandleRequest(
    private val handleError: HandleError,
    private val repository: OpenChatRepository
) {

    suspend fun handle(block: suspend () -> Unit) = try {
//        block.invoke()
//        MessageResult.Success(repository.getAllMessages())
    } catch (e: Exception) {
//        MessageResult.Failure(handleError.handle(e))
    }
}