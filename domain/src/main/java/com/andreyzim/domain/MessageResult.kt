package com.andreyzim.domain

sealed class MessageResult {

    interface Mapper<T> {
        fun map(list: List<MessageDomain>, errorMessage: String): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<MessageDomain> = emptyList()) : MessageResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list, "")
    }

    data class Failure(private val message: String) : MessageResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(emptyList(), message)
    }
}