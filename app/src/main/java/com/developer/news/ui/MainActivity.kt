package com.developer.news.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import com.developer.news.R
import com.developer.news.base.BaseActivity
import com.developer.news.data.source.Repository
import com.developer.news.data.source.RepositoryImpl
import com.developer.news.data.source.convertors.NewsEntityToNewsItem
import com.developer.news.data.source.convertors.NewsModelToNewsEntity
import com.developer.news.data.source.local.AppDB
import com.developer.news.data.source.local.LocalRepo
import com.developer.news.data.source.local.LocalRepoImpl
import com.developer.news.data.source.remote.ApiService
import com.developer.news.data.source.remote.NewsApi
import com.developer.news.data.source.remote.RemoteRepo
import com.developer.news.data.source.remote.RemoteRepoImpl
import com.developer.news.databinding.ActivityMainBinding
import com.developer.news.ui.home.HomeFragment
import com.developer.news.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity(
    @LayoutRes private val layout: Int = R.layout.activity_main
) : BaseActivity<ActivityMainBinding>(layout) {

    @Inject
    lateinit var repo: Repository

    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(repo))[MainViewModel::class.java]
    }

    override fun init(savedInstanceState: Bundle?, binding: ActivityMainBinding) {
        replaceFragment(R.id.fragment_container, HomeFragment(), addToStack = false)
    }
}