package com.andreyzim.domain.usecases

import com.andreyzim.domain.OpenChatInteractor
import com.andreyzim.domain.OpenChatRepository

class ClearMessagesUseCase(private val interactor: OpenChatInteractor) {

    suspend operator fun invoke() = interactor.clearMessages()
}