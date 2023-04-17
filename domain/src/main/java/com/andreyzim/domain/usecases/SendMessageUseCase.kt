package com.andreyzim.domain.usecases

import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.MessageResult
import com.andreyzim.domain.OpenChatInteractor
import com.andreyzim.domain.OpenChatRepository

class SendMessageUseCase(
//    private val interactor: OpenChatInteractor
    private val repository: OpenChatRepository
) {

    suspend operator fun invoke(body: String) =
        repository.sendMessage(body)
}