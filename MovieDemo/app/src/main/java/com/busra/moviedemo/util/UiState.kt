package com.busra.moviedemo.util

enum class SuccessType {
    NONE, RETURN_BACK
}

sealed class ErrorType {
    object NONE : ErrorType()
    data class DIALOG(val msg: String) : ErrorType()
    data class TOAST(val msg: String) : ErrorType()
}

sealed class UiStatus {
    data class Success(val successType: SuccessType) : UiStatus()
    data class Error(val errorType: ErrorType) : UiStatus()
    object Loading : UiStatus()
}
