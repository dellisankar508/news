package com.developer.news.ui.home

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import com.developer.news.R
import com.developer.news.base.BaseFragment
import com.developer.news.data.NewsItem
import com.developer.news.data.source.Repository
import com.developer.news.data.source.Result
import com.developer.news.databinding.FragmentHomeBinding
import com.developer.news.ui.MainViewModel
import com.developer.news.ui.ViewModelFactory
import com.developer.news.ui.details.DetailsFragment
import com.developer.news.utils.replaceFragment
import com.google.android.material.transition.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment(
    @LayoutRes private val layout: Int = R.layout.fragment_home
) : BaseFragment<FragmentHomeBinding>(layout) {


//    private val repo: Repository by lazy {
//        baseActivity!!.repo
//    }

    @Inject
    lateinit var repo: Repository

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory(
            repo
        )
    }

    private val newsAdapter =
        NewsListAdapter(clickListener = object : NewsListAdapter.ClickListener {
            override fun onClick(view: View, item: NewsItem) {
                enterTransition = MaterialElevationScale(false).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
                returnTransition = MaterialElevationScale(true).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
                ViewCompat.setTransitionName(view, item.title + item.id)
                viewModel.fetchNews(item.id)
                baseActivity!!.replaceFragment(
                    R.id.fragment_container,
                    DetailsFragment(),
                    true,
                    listOf(Pair(view, "hero_item"))
                )
            }
        })

    private lateinit var binding: FragmentHomeBinding

    override fun init(savedInstanceState: Bundle?, binding: FragmentHomeBinding) {
        this.binding = binding.apply {
            model = viewModel
            lifecycleOwner = this@HomeFragment
            newsList.adapter = newsAdapter
            refresh.setOnRefreshListener {
                viewModel.fetchLatestNews("us")
                refresh.isRefreshing = false
            }
        }

        viewModel.latestNews.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun render(state: Result<List<NewsItem>>) {
        when (state) {
            is Result.Success -> {
                newsAdapter.submitList(state.data)
            }
            is Result.Failed -> {
                binding.errorText.text = state.failure.message
            }
        }
    }
}