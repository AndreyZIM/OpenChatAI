package com.andreyzim.data.cloud

data class RequestBody(
    private val model: String = "gpt-3.5-turbo",
    private val messages: List<RequestMessage>
)

data class RequestMessage(
    private val role: String = "user",
    private val content: String
)


//"model": "text-davinci-003",
//"prompt": "Say this is a test",
//"max_tokens": 7,
//"temperature": 0,
//"top_p": 1,
//"n": 1,
//"stream": false,
//"logprobs": null,
//"stop": "\n"