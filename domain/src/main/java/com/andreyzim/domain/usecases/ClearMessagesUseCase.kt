package com.andreyzim.domain.usecases

import com.andreyzim.domain.OpenChatRepository

class ClearMessagesUseCase(private val repository: OpenChatRepository) {

    suspend operator fun invoke() = repository.clearMessages()
}