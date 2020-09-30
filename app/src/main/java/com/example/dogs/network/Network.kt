package com.example.dogs.network

import com.example.dogs.services.DogService
import com.squareup.okhttp.OkHttpClient
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Network {

    private val client = okhttp3.OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val dogService: DogService = retrofit.create<DogService>(DogService::class.java)
}