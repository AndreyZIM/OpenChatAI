package com.andreyzim.appcompose.di

import com.andreyzim.appcompose.ui.*
import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.MessageResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface PresentationModule {

    @Binds
    fun bindDispatchersList(dispatchersList: DispatchersList.Base): DispatchersList

    @Binds
    fun bindHandleRequest(handleRequest: HandleRequest.Base): HandleRequest

    @Binds
    fun bindToUIMapper(mapper: MessageToUIMapper): MessageDomain.Mapper<MessageUI>

    @Binds
    fun provideMessageResultMapper(mapper: MessageResultMapper): MessageResult.Mapper<DialogState>
}