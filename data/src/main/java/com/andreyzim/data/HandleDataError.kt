package com.andreyzim.data

import com.andreyzim.domain.HandleError
import com.andreyzim.domain.RequestTimeoutException
import com.andreyzim.domain.ServiceUnavailableException
import retrofit2.HttpException

class HandleDataError() : HandleError<Exception> {
    override fun handle(e: Exception): Exception = when (e) {
        is HttpException -> RequestTimeoutException()
        else -> ServiceUnavailableException()
    }
}