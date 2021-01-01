package com.busra.moviedemo.ui.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busra.moviedemo.util.*

open class BaseViewModel : ViewModel() {

    private val _isErrorDialog = MutableLiveData<Event<String>>()
    val isErrorDialog: LiveData<Event<String>> = _isErrorDialog

    private val _isErrorToast = MutableLiveData<Event<String>>()
    val isErrorToast: LiveData<Event<String>> = _isErrorToast

    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val isLoading: LiveData<Event<Boolean>> = _isLoading

    fun <T> handleResource(
        resource: Resource<T>,
        errorType: ErrorType = ErrorType.TOAST(resource.message)
    ) {
        when (resource.status) {
            is Status.ERROR -> {
                if (errorType is ErrorType.TOAST) {
                    setErrorToast(resource.message)
                } else if (errorType is ErrorType.DIALOG) {
                    setErrorDialog(resource.message)
                }
                setLoading(false)
            }
            is Status.LOADING -> {
                setLoading(true)
                setErrorToast("")
                setErrorDialog("")
            }
            is Status.SUCCESS -> {
                setLoading(false)
                setErrorToast("")
                setErrorDialog("")
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _isLoading.value = Event(isLoading)
    }

    private fun setErrorDialog(msg: String) {
        _isErrorDialog.value = Event(msg)
    }

    private fun setErrorToast(msg: String) {
        _isErrorToast.value = Event(msg)
    }
}