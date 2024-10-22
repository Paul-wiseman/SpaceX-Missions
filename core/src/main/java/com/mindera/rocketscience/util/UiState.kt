package com.mindera.rocketscience.util

sealed class UiState<T> {
    data class Error<T>(val message: UiText) : UiState<T>()
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
}
