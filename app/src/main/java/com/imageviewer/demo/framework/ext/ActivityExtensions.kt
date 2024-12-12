package com.imageviewer.demo.framework.ext

import android.app.Activity
import android.widget.Toast
import com.imageviewer.demo.R

fun Activity.showErrorMessage(
    message: String? = null
) {
    val msg = message ?: getString(R.string.something_went_wrong)
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}