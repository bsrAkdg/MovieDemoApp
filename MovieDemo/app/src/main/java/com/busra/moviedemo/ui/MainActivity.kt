package com.busra.moviedemo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.busra.moviedemo.R
import com.busra.moviedemo.databinding.ActivityMainBinding
import com.busra.moviedemo.ui.popularmovies.MovieViewModel
import com.busra.moviedemo.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewModel.isErrorDialog.observe(this, { it ->
            it?.getContentIfNotHandled()?.let { msg ->
                if (msg.isNotEmpty()) {
                    showWarningDialog(msg)
                }
            }
        })

        viewModel.isErrorToast.observe(this, {
            it?.getContentIfNotHandled()?.let { msg ->
                if (msg.isNotEmpty()) {
                    showToast(msg)
                }
            }
        })

        viewModel.isLoading.observe(this, {
            it?.getContentIfNotHandled()?.let { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        })
    }
}