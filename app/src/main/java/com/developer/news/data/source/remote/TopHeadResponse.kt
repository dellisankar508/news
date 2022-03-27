package com.developer.news.data.source.remote

data class TopHeadLinesResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<NewsModel>
)

data class NewsModel(
    val source: SourceModel,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceModel(val id: String?, val name: String)

enum class Status(
    val value: String
) {
    OK("ok"),
    ERROR("error")
}
