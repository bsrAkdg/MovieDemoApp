package com.busra.moviedemo.util

import android.content.Context
import android.widget.Toast
import com.busra.moviedemo.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showWarningDialog(message: String) {
    MaterialAlertDialogBuilder(this)
        .setTitle("Warning")
        .setMessage(message)
        .setPositiveButton(resources.getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }.show()
}