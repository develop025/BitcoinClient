package com.example.mining.network

import com.example.mining.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object MiningApi {
    private const val BASE_URL = "https://mining-server.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val miningApiService: MiningApiService by lazy {
        retrofit.create(MiningApiService::class.java)
    }
}