package com.andreyzim.appcompose.ui

sealed interface DialogState {

    data class Success(val messageList: List<MessageUI>) : DialogState

    object Loading : DialogState

    class Error(message: String) : DialogState

}
