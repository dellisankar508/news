package com.developer.news.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developer.news.data.NewsItem
import com.developer.news.data.source.Repository
import com.developer.news.data.source.Result
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repository) : ViewModel() {

    val latestNews: LiveData<Result<List<NewsItem>>> = repo.latestNews
    val news: LiveData<Result<NewsItem?>> = repo.news

    init {
        fetchLatestNews("us")
    }

    fun fetchLatestNews(country: String) {
        viewModelScope.launch {
            repo.getLatestNews(country = country)
        }
    }

    fun fetchNews(id: Long) {
        viewModelScope.launch {
            repo.getNewsById(key = id)
        }
    }
}