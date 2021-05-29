package com.example.fintonicbeer.service

import com.example.fintonicbeer.model.Beer
import io.reactivex.Single
import retrofit2.http.GET

interface BeerAPI {
    @GET("beers?page=10")
    fun getBeers(): Single<List<Beer>>
}