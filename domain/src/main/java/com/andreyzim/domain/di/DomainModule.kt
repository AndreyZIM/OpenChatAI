package com.andreyzim.domain.di

import com.andreyzim.domain.OpenChatRepository
import com.andreyzim.domain.usecases.ClearMessagesUseCase
import com.andreyzim.domain.usecases.SendMessageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    // TODO handleDomainRequest DI

    @Provides
    fun provideClearMessagesUseCase(repository: OpenChatRepository): ClearMessagesUseCase =
        ClearMessagesUseCase(repository)

    @Provides
    fun provideSendMessageUseCase(repository: OpenChatRepository): SendMessageUseCase =
        SendMessageUseCase(repository)
}