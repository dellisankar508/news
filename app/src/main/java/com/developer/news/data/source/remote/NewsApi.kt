package com.developer.news.data.source.remote

import com.developer.news.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsApi {

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()).build()

    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(Interceptor {
            val req = it.request()
            val url = req.url.newBuilder().addQueryParameter("apikey", BuildConfig.API_KEY).build()
            return@Interceptor it.proceed(req.newBuilder().url(url).build())
        }).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}