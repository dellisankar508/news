package com.developer.news.data.source.local

import javax.inject.Inject

class LocalRepoImpl @Inject constructor(private val dao: NewsDao) : LocalRepo {

    override suspend fun getLatestNews(): List<NewsEntity> {
        return dao.getLatestNews()
    }

    override suspend fun getNewsById(key: Long): NewsEntity? {
        return dao.getByID(key)
    }

    override suspend fun insertAll(list: List<NewsEntity>) {
        dao.insertAll(*list.toTypedArray())
    }

    override suspend fun deleteAll() {
        dao.clear()
    }
}