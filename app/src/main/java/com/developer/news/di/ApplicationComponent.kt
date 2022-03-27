package com.developer.news.di

import android.content.Context
import com.developer.news.data.source.local.AppDB
import com.developer.news.data.source.local.NewsDao
import com.developer.news.data.source.remote.ApiService
import com.developer.news.data.source.remote.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationComponent {

    @Provides
    fun provideApiService(): ApiService {
        return NewsApi.service
    }

    @Provides
    fun provideDAO(@ApplicationContext context: Context): NewsDao {
        return AppDB.getInstance(context).newsDao()
    }

}