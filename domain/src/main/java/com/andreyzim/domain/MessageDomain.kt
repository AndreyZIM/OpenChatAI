package com.andreyzim.domain

class MessageDomain(
    private val message: String,
    private val created: Long,
    private val received: Boolean
) {

    interface Mapper<T> {
        fun map(message: String, created: Long, received: Boolean): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(message, created, received)
}