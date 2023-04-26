package com.andreyzim.domain.di

import com.andreyzim.domain.HandleDomainRequest
import com.andreyzim.domain.HandleError
import com.andreyzim.domain.OpenChatInteractor
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

    @Provides
    fun provideClearMessagesUseCase(interactor: OpenChatInteractor): ClearMessagesUseCase =
        ClearMessagesUseCase(interactor)

    @Provides
    fun provideSendMessageUseCase(interactor: OpenChatInteractor): SendMessageUseCase =
        SendMessageUseCase(interactor)

    @Provides
    fun provideInteractor(
        repository: OpenChatRepository,
        handleDomainRequest: HandleDomainRequest
    ): OpenChatInteractor = OpenChatInteractor.Base(repository, handleDomainRequest)

    @Provides
    fun provideHandleRequest(handleError: HandleError<String>): HandleDomainRequest =
        HandleDomainRequest.Base(handleError)

    @Provides
    fun provideHandleError(): HandleError<String> = HandleError.Base()
}