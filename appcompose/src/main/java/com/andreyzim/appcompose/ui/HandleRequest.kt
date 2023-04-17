package com.andreyzim.appcompose.ui

import com.andreyzim.domain.MessageResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


interface HandleRequest {

    fun handle(
        coroutineScope: CoroutineScope,
        block: suspend () -> MessageResult
    )

    class Base @Inject constructor(
        private val dispatchers: DispatchersList,
//        private val messageResultMapper: MessageResult.Mapper<Unit>
    ) : HandleRequest {

        override fun handle(
            coroutineScope: CoroutineScope,
            block: suspend () -> MessageResult
        ) {
            //TODO add loading
            coroutineScope.launch(dispatchers.io()) {
                val result = block.invoke()
//                result.map(messageResultMapper)
            }
        }
    }
}