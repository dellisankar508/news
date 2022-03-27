package com.developer.news.data.source.convertors

import com.developer.news.data.source.local.NewsEntity
import com.developer.news.data.source.local.Source
import com.developer.news.data.source.remote.NewsModel
import javax.inject.Inject

class NewsModelToNewsEntity @Inject constructor(): Convertor<NewsModel, NewsEntity> {
    override fun convert(model: NewsModel): NewsEntity {
        return NewsEntity(
            id = 0L,
            source = Source(model.source.id, model.source.name),
            author = model.author,
            title = model.title,
            description = model.description,
            url = model.url,
            urlToImage = model.urlToImage,
            publishedAt = model.publishedAt,
            content = model.content
        )
    }

    override fun convert(models: List<NewsModel>): List<NewsEntity> {
        return models.map {
            convert(it)
        }
    }
}