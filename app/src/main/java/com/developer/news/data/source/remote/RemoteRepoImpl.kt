package com.developer.news.data.source.remote

import android.content.Context
import android.util.Log
import com.developer.news.R
import com.developer.news.data.source.Failure
import dagger.hilt.android.qualifiers.ApplicationContext
import java.net.UnknownHostException
import javax.inject.Inject

class RemoteRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService,
) : RemoteRepo {

    override suspend fun getTopHeadLines(country: String): ApiResult<List<NewsModel>> {
        return try {
            val response = apiService.getLatestNews(country)
            ApiResult.Success(response.articles)
        } catch (ex: Exception) {
            Log.d("Testing", ex.toString())
            ApiResult.Failed(exceptionHandle(context, ex))
        }
    }

    companion object {

        const val UNKNOWN_ERROR_CODE = 0
        const val INTERNET_ERROR_CODE = 1

        fun exceptionHandle(context: Context, exception: Exception): Failure {
            return when (exception) {
                is UnknownHostException -> {
                    Failure(
                        code = INTERNET_ERROR_CODE,
                        message = context.getString(R.string.internet_error),
                        exception = exception
                    )
                }
                else -> {
                    Failure(
                        code = UNKNOWN_ERROR_CODE,
                        message = context.getString(R.string.something_wrong),
                        exception = exception
                    )
                }
            }
        }
    }
}