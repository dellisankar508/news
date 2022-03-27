package com.developer.news.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.replaceFragment(
    @IdRes containerId: Int,
    fragment: Fragment,
    addToStack: Boolean = true,
    sharedElement: List<Pair<View, String>> = emptyList()
) {
    supportFragmentManager.beginTransaction().apply {
        if (sharedElement.isNotEmpty()) setReorderingAllowed(true)
        sharedElement.forEach {
            addSharedElement(it.first, it.second)
        }
        if (addToStack) addToBackStack(null)
        replace(containerId, fragment)
    }.commit()
}