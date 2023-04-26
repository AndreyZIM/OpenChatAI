@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.MessageResult
import javax.inject.Inject

class MessageResultMapper @Inject constructor(
    private val mapper: MessageDomain.Mapper<MessageUI>
) : MessageResult.Mapper<MessageListStateState> {
    override fun map(list: List<MessageDomain>, errorMessage: String): MessageListStateState =
        if (errorMessage.isEmpty())
            MessageListStateState.Success(list.map { it.map(mapper) })
        else
            MessageListStateState.Error(errorMessage)
}