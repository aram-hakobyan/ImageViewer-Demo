package com.imageviewer.demo.presentation.binding

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.imageviewer.demo.domain.model.Image
import com.imageviewer.demo.presentation.home.adapter.ImageListAdapter

@BindingAdapter("errorText")
fun setErrorText(
    editText: EditText,
    errorText: String?
) {
    editText.error = errorText
}

@BindingAdapter("errorText")
fun setErrorText(textView: TextView, errorMessage: String?) {
    textView.text = errorMessage
}

@BindingAdapter("errorTextVisibility")
fun setErrorVisibility(textView: TextView, isVisible: Boolean) {
    textView.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onItemClick")
fun setOnItemClickListener(
    view: View,
    clickListener: ImageListAdapter.OnItemClickListener?
) {
    view.setOnClickListener {
        val item = view.tag as? Image
        if (item != null && clickListener != null) {
            clickListener.onItemClick(item)
        }
    }
}

@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    url: String?
) {
    view.load(url) {
        // placeholder(R.drawable.placeholder)
    }
}

