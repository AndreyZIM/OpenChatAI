package com.andreyzim.appcompose.ui

import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.MessageResult
import javax.inject.Inject

class MessageResultMapper @Inject constructor(
    private val mapper: MessageDomain.Mapper<MessageUI>
) : MessageResult.Mapper<DialogState> {
    override fun map(list: List<MessageDomain>, errorMessage: String): DialogState =
        if (errorMessage.isEmpty())
            DialogState.Success(list.map { it.map(mapper) })
        else
            DialogState.Error(errorMessage)
}