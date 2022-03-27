package com.developer.news.data.source.remote

import com.developer.news.data.source.Failure

sealed class ApiResult<T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Failed<T>(val failure: Failure): ApiResult<T>()
}
