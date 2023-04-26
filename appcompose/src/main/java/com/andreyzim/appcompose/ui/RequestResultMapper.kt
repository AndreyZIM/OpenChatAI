@file:OptIn(ExperimentalMaterial3Api::class)

package com.andreyzim.appcompose.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import com.andreyzim.domain.RequestResult
import javax.inject.Inject

class RequestResultMapper @Inject constructor() : RequestResult.Mapper<UiState> {
    override fun map(errorMessage: String): UiState =
        if (errorMessage.isEmpty()) UiState.Success else UiState.RequestError(errorMessage)
}