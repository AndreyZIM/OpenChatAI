package com.andreyzim.data

import com.andreyzim.domain.HandleError
import com.andreyzim.domain.RequestResult

interface HandleDataRequest {

    suspend fun handle(block: suspend () -> Unit): RequestResult

    class Base(
        private val handleError: HandleError<Exception>
    ) : HandleDataRequest {
        override suspend fun handle(block: suspend () -> Unit) = try {
            block.invoke()
            RequestResult.Success
        } catch (e: Exception) {
            throw handleError.handle(e)
        }
    }
}