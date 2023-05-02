package com.andreyzim.appcompose.ui

sealed interface UiState {

    object Waiting: UiState

    object Success: UiState

    data class TypingError(val message: String): UiState

    data class RequestError(val message: String) : UiState
}
