package com.busra.moviedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.busra.moviedemo.R
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import com.busra.moviedemo.util.ErrorType
import com.busra.moviedemo.util.ErrorType.*
import com.busra.moviedemo.util.UiStatus

class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.mainStateView.observe(this, {
            it?.getContentIfNotHandled()?.let { mainViewState ->
                if (mainViewState.uiStatus is ErrorType) {
                    when((mainViewState.uiStatus as UiStatus.Error).errorType) {
                        is DIALOG -> {
                            // show dialog
                        }
                        is TOAST -> {
                            // show toast
                        }
                        else -> {

                        }
                    }
                }
            }
        })
    }
}