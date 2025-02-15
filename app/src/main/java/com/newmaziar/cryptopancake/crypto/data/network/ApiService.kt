package com.newmaziar.cryptopancake.crypto.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private const val BASE_URL_FRANK_FUTURE = "https://api.frankfurter.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }


    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()


    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL_FRANK_FUTURE)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    fun provideService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)


}