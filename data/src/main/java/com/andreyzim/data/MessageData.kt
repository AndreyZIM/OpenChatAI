package com.andreyzim.data

data class MessageData(
    val message: String,
    val created: Long,
    val received: Boolean
) {

    interface Mapper<T> {
        fun map(message: String, created: Long, received: Boolean): T
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(message, created, received)
}