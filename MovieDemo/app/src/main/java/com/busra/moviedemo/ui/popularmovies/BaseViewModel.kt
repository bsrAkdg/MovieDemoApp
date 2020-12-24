package com.busra.moviedemo.ui.popularmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busra.moviedemo.ui.MainViewState
import com.busra.moviedemo.util.*

open class BaseViewModel : ViewModel() {

    private val _mainStateView = MutableLiveData<Event<MainViewState>>()
    val mainStateView: LiveData<Event<MainViewState>> = _mainStateView

    private fun setMainStateView(mainViewState: MainViewState) {
        _mainStateView.value = Event(mainViewState)
    }

    fun <T> handleErrorLoadingResource(
        resource: Resource<T>,
        errorType: ErrorType = ErrorType.TOAST(resource.message)
    ) {
        if (resource.status is Status.ERROR) {
            setMainStateView(
                MainViewState(
                    uiStatus = UiStatus.Error(errorType)
                )
            )
        }
        else if (resource.status is Status.LOADING) {
            setMainStateView(
                MainViewState(
                    uiStatus = UiStatus.Loading
                )
            )
        }
    }
}