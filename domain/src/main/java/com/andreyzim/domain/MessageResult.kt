package com.andreyzim.domain

sealed class RequestResult {

    interface Mapper<T> {
        fun map(errorMessage: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    object Success : RequestResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map("")
    }

    data class Failure(private val message: String) : RequestResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(message)
    }
}