package com.imageviewer.demo.data.api.mapper

import com.imageviewer.demo.data.api.response.ImageEntity
import com.imageviewer.demo.domain.model.Image

fun ImageEntity.toDomain(): Image {
    return Image(
        id = id,
        thumbnailUrl = previewURL,
        imageUrl = largeImageURL,
        user = user,
        userPhotoUrl = userImageURL,
        size = "$imageWidth x $imageHeight",
        type = type,
        tags = tags,
        views = views.toString(),
        likes = likes.toString(),
        comments = comments.toString(),
        downloads = downloads.toString(),
        favorites = likes.toString()
    )
}
