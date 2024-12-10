package com.pixabay.demo.data.entity

import com.pixabay.demo.domain.model.Photo

fun PhotoEntity.toDomain(): Photo {
    return Photo(
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
