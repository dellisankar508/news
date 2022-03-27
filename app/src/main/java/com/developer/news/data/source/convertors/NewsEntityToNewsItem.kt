package com.developer.news.data.source.convertors

import com.developer.news.data.NewsItem
import com.developer.news.data.source.local.NewsEntity
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

class NewsEntityToNewsItem @Inject constructor(): Convertor<NewsEntity, NewsItem> {

    override fun convert(models: List<NewsEntity>): List<NewsItem> {
        return models.map {
            convert(it)
        }
    }

    override fun convert(model: NewsEntity): NewsItem {
        return NewsItem(
            id = model.id,
            sourceId = model.source.sourceId,
            sourceName = model.source.sourceName,
            author = model.author,
            title = model.title,
            description = model.description,
            url = model.url,
            urlToImage = model.urlToImage,
            publishedAt = model.publishedAt,
            content = model.content
        )
    }
}