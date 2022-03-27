package com.developer.news.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.developer.news.R
import com.developer.news.data.NewsItem
import com.developer.news.data.source.convertors.NewsEntityToNewsItem
import com.developer.news.data.source.convertors.NewsModelToNewsEntity
import com.developer.news.data.source.local.LocalRepo
import com.developer.news.data.source.local.NewsEntity
import com.developer.news.data.source.remote.ApiResult
import com.developer.news.data.source.remote.RemoteRepo
import com.developer.news.data.source.remote.RemoteRepoImpl.Companion.INTERNET_ERROR_CODE
import com.developer.news.data.source.remote.RemoteRepoImpl.Companion.UNKNOWN_ERROR_CODE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localRepo: LocalRepo,
    private val remoteRepo: RemoteRepo,
    private val convertToNewsEntity: NewsModelToNewsEntity,
    private val convertToNewsItem: NewsEntityToNewsItem
) : Repository {

    private val _latestNews: MutableLiveData<Result<List<NewsItem>>> = MutableLiveData()
    override val latestNews: LiveData<Result<List<NewsItem>>> = _latestNews

    private val _news: MutableLiveData<Result<NewsItem?>> = MutableLiveData()
    override val news: LiveData<Result<NewsItem?>> = _news

    override suspend fun getLatestNews(country: String) {
        _latestNews.postValue(Result.Loading)
        when (val apiResult = remoteRepo.getTopHeadLines(country)) {
            is ApiResult.Success -> {
                if (apiResult.data.isNotEmpty()) {
                    localRepo.deleteAll()
                    localRepo.insertAll(convertToNewsEntity.convert(apiResult.data))
                }
                _latestNews.postValue(Result.Success(convertToNewsItem.convert(getCachedNews())))
            }
            is ApiResult.Failed -> {
                when (apiResult.failure.code) {
                    INTERNET_ERROR_CODE -> {
                        _latestNews.postValue(Result.Success(convertToNewsItem.convert(getCachedNews())))
                    }
                    UNKNOWN_ERROR_CODE -> {
                        _latestNews.postValue(Result.Failed(apiResult.failure))
                    }
                }
            }
        }
    }

    override suspend fun getNewsById(key: Long) {
        _news.postValue(Result.Loading)
        val selectedNews = localRepo.getNewsById(key)
        _news.postValue(
            if (selectedNews != null) Result.Success(convertToNewsItem.convert(selectedNews))
            else Result.Failed(
                Failure(
                    UNKNOWN_ERROR_CODE,
                    context.getString(R.string.something_wrong),
                    null
                )
            )
        )
    }

    private suspend fun getCachedNews(): List<NewsEntity> {
        return localRepo.getLatestNews()
    }
}