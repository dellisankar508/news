package com.developer.news.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.developer.news.data.NewsItem
import com.developer.news.databinding.NewsItemBinding

class NewsListAdapter(private val clickListener: ClickListener) :
    ListAdapter<NewsItem, NewsListAdapter.NewsViewHolder>(diffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    inner class NewsViewHolder(
        private val binding: NewsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsItem, clickListener: ClickListener) {
            binding.apply {
                item = news
                listener = clickListener
                executePendingBindings()
            }
        }
    }

    interface ClickListener {
        fun onClick(view: View, item: NewsItem)
    }

    companion object {
        val diffItemCallback = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}