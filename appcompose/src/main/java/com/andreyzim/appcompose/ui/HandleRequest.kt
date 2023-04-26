package com.andreyzim.appcompose.ui

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


interface HandleRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> Unit
    ) : UiState

    class Base @Inject constructor(
        private val dispatchers: DispatchersList,
    ) : HandleRequest {

        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend () -> Unit
        ): UiState {
            coroutineScope.launch(dispatchers.io()) {
                block.invoke()
            }
            return UiState.Success
        }
    }
}