package com.developer.news.di

import com.developer.news.data.source.Repository
import com.developer.news.data.source.RepositoryImpl
import com.developer.news.data.source.local.LocalRepo
import com.developer.news.data.source.local.LocalRepoImpl
import com.developer.news.data.source.remote.RemoteRepo
import com.developer.news.data.source.remote.RemoteRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsLocalRepo(localRepoImpl: LocalRepoImpl): LocalRepo

    @Binds
    abstract fun bindsRemoteRepo(remoteRepoImpl: RemoteRepoImpl): RemoteRepo

    @Binds
    abstract fun bindsRepository(repositoryImpl: RepositoryImpl): Repository
}