package com.busra.moviedemo

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * We need to create such a debug source set in which we will create our actual
 * custom test hilt activity so that activity that is annotated with at android entry point
 * and on which we then will attach our fragment (add new package debug into src folder class)
 *
 * */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()