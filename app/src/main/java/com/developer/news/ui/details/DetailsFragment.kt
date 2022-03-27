package com.developer.news.ui.details

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import com.developer.news.R
import com.developer.news.base.BaseFragment
import com.developer.news.data.NewsItem
import com.developer.news.data.source.Repository
import com.developer.news.data.source.Result
import com.developer.news.databinding.FragmentDetailsBinding
import com.developer.news.ui.MainViewModel
import com.developer.news.ui.ViewModelFactory
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment(
    @LayoutRes private val layout: Int = R.layout.fragment_details
) : BaseFragment<FragmentDetailsBinding>(layout) {

//    private val repo: Repository by lazy {
//        baseActivity!!.repo
//    }

    @Inject
    lateinit var repo: Repository

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory(repo)
    }

    private lateinit var binding: FragmentDetailsBinding

    override fun init(savedInstanceState: Bundle?, binding: FragmentDetailsBinding) {
        postponeEnterTransition(5000, TimeUnit.MILLISECONDS)
        val imageView: MaterialCardView = binding.parent
        ViewCompat.setTransitionName(imageView, "hero_item")
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        viewModel.news.observe(viewLifecycleOwner) {
            bind(it)
        }
        this.binding = binding.apply {
            backBtn.setOnClickListener {
                baseActivity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun bind(data: Result<NewsItem?>) {
        binding.apply {
            when (data) {
                is Result.Success -> {
                    item = data.data
                    startPostponedEnterTransition()
                }
                is Result.Failed -> {}
            }
        }
    }
}