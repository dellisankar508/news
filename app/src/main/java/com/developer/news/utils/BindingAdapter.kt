package com.developer.news.utils

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.developer.news.R
import com.developer.news.data.NewsItem
import com.developer.news.data.source.Result
import com.developer.news.ui.home.NewsListAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("imageUrl")
fun ImageView.setImage(url: String?) {
    if (url != null) {
        load(url) {
            placeholder(R.drawable.ic_launcher_foreground)
        }
    } else {
        load(R.drawable.ic_launcher_foreground)
    }
}

@BindingAdapter("date")
fun displayDate(view: TextView, date: String?) {
    view.text = date?.getDisplayableDate()
}

@BindingAdapter("fullScreen")
fun View.setFullScreen(fullscreen: Boolean) {
    systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
}

@BindingAdapter(
    "marginStartSystemInsets",
    "marginTopSystemInsets",
    "marginEndSystemInsets",
    "marginBottomSystemInsets", requireAll = false
)
fun View.applyMargin(
    marginStart: Boolean,
    marginTop: Boolean,
    marginEnd: Boolean,
    marginBottom: Boolean
) {
    applyInsets { view, windowInsetsCompat, initialMargin, _ ->
        val start = if (marginStart) windowInsetsCompat.systemWindowInsetLeft else 0
        val top = if (marginTop) windowInsetsCompat.systemWindowInsetTop else 0
        val end = if (marginEnd) windowInsetsCompat.systemWindowInsetRight else 0
        val bottom = if (marginBottom) windowInsetsCompat.systemWindowInsetBottom else 0

        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = initialMargin.start + start
            topMargin = initialMargin.top + top
            rightMargin = initialMargin.end + end
            bottomMargin = initialMargin.bottom + bottom
        }
    }
}

@BindingAdapter(
    "paddingStartSystemInsets",
    "paddingTopSystemInsets",
    "paddingEndSystemInsets",
    "paddingBottomSystemInsets", requireAll = false
)
fun View.applyPadding(
    paddingStart: Boolean,
    paddingTop: Boolean,
    paddingEnd: Boolean,
    paddingBottom: Boolean
) {
    applyInsets { view, windowInsetsCompat, _, initialPadding ->
        val start = if (paddingStart) windowInsetsCompat.systemWindowInsetLeft else 0
        val top = if (paddingTop) windowInsetsCompat.systemWindowInsetTop else 0
        val end = if (paddingEnd) windowInsetsCompat.systemWindowInsetRight else 0
        val bottom = if (paddingBottom) windowInsetsCompat.systemWindowInsetBottom else 0

        view.updatePadding(
            left = initialPadding.start + start,
            top = initialPadding.top + top,
            right = initialPadding.end + end,
            bottom = initialPadding.bottom + bottom
        )
    }
}

fun View.applyInsets(block: (View, WindowInsetsCompat, InitialMargin, InitialPadding) -> Unit) {
    val initialMargin: InitialMargin = recordMargin(this)
    val initialPadding: InitialPadding = recordPadding(this)
    ViewCompat.setOnApplyWindowInsetsListener(
        this
    ) { v, insets ->
        block(v, insets, initialMargin, initialPadding)
        insets
    }
    requestApplyInsetsWhenAttached()
}

data class InitialMargin(val start: Int, val top: Int, val end: Int, val bottom: Int)
data class InitialPadding(val start: Int, val top: Int, val end: Int, val bottom: Int)

private fun recordMargin(view: View): InitialMargin {
    val lp = view.layoutParams as ViewGroup.MarginLayoutParams
    return InitialMargin(lp.marginStart, lp.topMargin, lp.marginEnd, lp.bottomMargin)
}

private fun recordPadding(v: View): InitialPadding {
    return InitialPadding(
        v.paddingStart,
        v.paddingTop,
        v.paddingEnd,
        v.paddingBottom
    )
}

private fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(p0: View?) {
                p0?.removeOnAttachStateChangeListener(this)
                p0?.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(p0: View?) {}
        })
    }
}