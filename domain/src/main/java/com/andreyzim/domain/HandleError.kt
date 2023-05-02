package com.andreyzim.domain

interface HandleError<T> {

    fun handle(e: Exception): T

    class Base(
        // TODO manageResources
    ) : HandleError<String> {
        override fun handle(e: Exception): String = when (e) {
            is RequestTimeoutException -> "Request timeout."
            else -> "Service Unavailable."
        }
    }
}