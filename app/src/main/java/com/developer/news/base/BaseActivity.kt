package com.developer.news.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.developer.news.data.source.Repository

abstract class BaseActivity<DB : ViewDataBinding>(
    @LayoutRes private val layout: Int
) : AppCompatActivity() {

//    abstract val repo: Repository

    abstract fun init(savedInstanceState: Bundle?, binding: DB)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<DB>(this, layout).apply {
            lifecycleOwner = this@BaseActivity
        }.also {
            init(savedInstanceState, it)
        }
    }
}