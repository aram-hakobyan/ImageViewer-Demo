package com.pixabay.demo.domain.model

data class Image(
    val id: Int,
    val thumbnailUrl: String,
    val imageUrl: String,
    val user: String,
    val userPhotoUrl: String,
    val size: String,
    val type: String,
    val tags: String,
    val views: String,
    val likes: String,
    val comments: String,
    val favorites: String,
    val downloads: String
)