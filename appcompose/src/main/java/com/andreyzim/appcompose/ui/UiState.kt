package com.andreyzim.appcompose.ui

sealed interface UiState {

    object Waiting: UiState

    object Success: UiState

    data class Error(val message: String): UiState
}
