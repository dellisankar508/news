package com.developer.news.data.source.convertors

interface Convertor<in S, out T> {

    fun convert(model: S): T
    fun convert(models: List<S>): List<T>
}