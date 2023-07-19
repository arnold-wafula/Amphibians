package com.lemonboy.amphibians.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.lemonboy.amphibians.network.AmphibianApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}

class DefaultContainer: AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: AmphibianApiService by lazy {
        retrofit.create(AmphibianApiService::class.java)
    }

    override val amphibiansRepository: AmphibiansRepository by lazy {
        NetworkAmphibiansRepository(retrofitService)
    }
}