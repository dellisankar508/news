package com.developer.news.data.source.local

interface LocalRepo {

    suspend fun getLatestNews(): List<NewsEntity>
    suspend fun getNewsById(key: Long): NewsEntity?
    suspend fun insertAll(list: List<NewsEntity>)
    suspend fun deleteAll()
}