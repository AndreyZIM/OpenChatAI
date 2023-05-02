package com.andreyzim.domain



interface HandleDomainRequest {
    suspend fun handle(block: suspend () -> Unit): RequestResult

    class Base(
        private val handleError: HandleError<String>
    ) : HandleDomainRequest {
        override suspend fun handle(block: suspend () -> Unit): RequestResult = try{
            block.invoke()
            RequestResult.Success
        } catch (e: Exception) {
            RequestResult.Failure(handleError.handle(e))
        }
    }
}
