package com.dani.data.datasource.remote.module

import android.util.Log
import com.dani.data.datasource.remote.StarWarsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(HttpLoggingInterceptor { message ->
                Log.e("Network Log", message)
            }.apply {
                level= HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    fun providesApiService(json: Json, okHttpClient: OkHttpClient): StarWarsApiService {

        return Retrofit.Builder()
            .baseUrl("https://swapi.dev/api/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(StarWarsApiService::class.java)
    }
}
