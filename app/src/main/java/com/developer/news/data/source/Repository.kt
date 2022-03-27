package com.developer.news.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.news.data.NewsItem

interface Repository {

    val latestNews: LiveData<Result<List<NewsItem>>>
    val news: LiveData<Result<NewsItem?>>

    suspend fun getLatestNews(country: String)
    suspend fun getNewsById(key: Long)
}