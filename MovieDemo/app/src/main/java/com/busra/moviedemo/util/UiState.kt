package com.busra.moviedemo.util

sealed class ErrorType {
    object NONE : ErrorType()
    data class DIALOG(val msg: String) : ErrorType()
    data class TOAST(val msg: String) : ErrorType()
}