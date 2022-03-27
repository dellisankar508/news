package com.developer.news.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : Fragment() {

    val baseActivity by lazy { activity as? BaseActivity<*> }

    abstract fun init(savedInstanceState: Bundle?, binding: DB)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<DB>(inflater, layout, container, false).apply {
            lifecycleOwner = this@BaseFragment
        }.also {
            init(savedInstanceState, it)
        }.root
    }
}