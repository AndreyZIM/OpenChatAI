package com.andreyzim.data.cloud

import com.andreyzim.data.MessageData
import com.squareup.moshi.Json

data class ReceivedMessage(
    @field:Json(name = "choices") val choices: List<Choice>
) {
    fun toMessageData() : MessageData = MessageData(choices.first().message.content, System.currentTimeMillis(), true)
}

data class Choice(
    @field:Json(name = "message") val message: Message
)

data class Message(
    @field:Json(name = "content") val content: String
)
