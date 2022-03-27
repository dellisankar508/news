package com.developer.news.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert
    suspend fun insertAll(vararg news: NewsEntity)

    @Query("DELETE FROM NewsEntity")
    suspend fun clear()

    @Query("SELECT * FROM NewsEntity WHERE id = :key")
    suspend fun getByID(key: Long): NewsEntity?

    @Query("SELECT * FROM NewsEntity")
    suspend fun getLatestNews(): List<NewsEntity>

}