package com.developer.news.data.source

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
    data class Failed<T>(val failure: Failure) : Result<T>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isFailed() = this is Failed
}
