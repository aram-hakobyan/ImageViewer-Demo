package com.pixabay.demo.ui.login

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.pixabay.demo.domain.model.Photo
import com.pixabay.demo.ui.home.ImageListAdapter

@BindingAdapter("errorText")
fun setErrorText(
    view: EditText,
    errorText: String?
) {
    view.error = errorText
}

@BindingAdapter("onItemClick")
fun setOnItemClickListener(
    view: View,
    clickListener: ImageListAdapter.OnItemClickListener?
) {
    view.setOnClickListener {
        val item = view.tag as? Photo
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
        // error(R.drawable.error_image)
    }
}

