package com.andreyzim.domain.usecases

import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.OpenChatInteractor
import com.andreyzim.domain.OpenChatRepository
import com.andreyzim.domain.RequestResult

class SendMessageUseCase(
    private val interactor: OpenChatInteractor
) {

    suspend operator fun invoke(body: String): RequestResult =
        interactor.sendMessage(body)
}