@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyzim.domain.MessageDomain
import com.andreyzim.domain.OpenChatRepository
import com.andreyzim.domain.usecases.ClearMessagesUseCase
import com.andreyzim.domain.usecases.SendMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenChatViewModel @Inject constructor(
    private val repository: OpenChatRepository,
    private val sendMessageUseCase: SendMessageUseCase,
    private val clearMessageUseCase: ClearMessagesUseCase,
    private val mapper: MessageDomain.Mapper<MessageUI>
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Success)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UiState.Success
        )

    val messageListStateState: StateFlow<MessageListStateState> = repository.data
        .map {
            it.map {messageDomain ->  messageDomain.map(mapper)}
        }
        .map {
            MessageListStateState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MessageListStateState.Loading
        )


    fun sendMessage(body: String) {
        if (body.isEmpty()) {
            _uiState.value = UiState.Error("Field is empty.")
        } else {
            _uiState.value = UiState.Waiting
            viewModelScope.launch(Dispatchers.IO) {
                Log.i("SEND", "viewModel: Body: $body")
                sendMessageUseCase(body)
                _uiState.value = UiState.Success
            }
        }
    }

    fun resetError() {
        _uiState.value = UiState.Success
    }

    fun clearMessages() {
        _uiState.value = UiState.Waiting
        viewModelScope.launch(Dispatchers.IO) {
            clearMessageUseCase()
            _uiState.value = UiState.Success
        }
    }
}