package com.developer.news.data.source.remote

interface RemoteRepo {

    suspend fun getTopHeadLines(country: String): ApiResult<List<NewsModel>>
}