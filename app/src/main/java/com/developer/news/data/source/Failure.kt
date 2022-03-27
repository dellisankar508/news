package com.developer.news.data.source

data class Failure(
    val code: Int?,
    val message: String,
    val exception: Exception? = null
)
