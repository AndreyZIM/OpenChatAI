@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import androidx.compose.material3.ExperimentalMaterial3Api

sealed interface MessageListStateState {

    data class Success(val messageList: List<MessageUI>) : MessageListStateState

    object Loading : MessageListStateState

    class Error(message: String) : MessageListStateState
}
