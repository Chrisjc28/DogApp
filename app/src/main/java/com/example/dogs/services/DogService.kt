package com.example.dogs.services

import com.example.dogs.models.BreedResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface DogService {

    @GET("breed/{breed}/images/")
    fun fetchBreed(@Path("breed") breed: String?): Single<BreedResponse>?

    @GET("breeds/image/random/20")
    fun getRandomDogs(): Single<BreedResponse>?
}