package com.developer.news.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v2/top-headlines")
    suspend fun getLatestNews(
        @Query("country") countryCode: String,
        @Query("pageSize") pageSize: Int = 100
    ): TopHeadLinesResponse
}