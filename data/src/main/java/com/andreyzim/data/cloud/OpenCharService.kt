package com.andreyzim.data.cloud

import com.andreyzim.data.Constants.API_KEY
import com.andreyzim.data.MessageData
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenChatService {

    @Headers(
        "content-type: application/json",
        "X-RapidAPI-Key: $API_KEY",
        "X-RapidAPI-Host: openai80.p.rapidapi.com"
    )
    @POST("chat/completions")
    suspend fun send(@Body body: RequestBody): ReceivedMessage
}