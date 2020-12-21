package com.busra.moviedemo.ui

import com.busra.moviedemo.util.UiStatus

data class MainViewState(
    val uiStatus: UiStatus
) {
    val isLoading = uiStatus is UiStatus.Loading
    val isError = uiStatus is UiStatus.Error
}