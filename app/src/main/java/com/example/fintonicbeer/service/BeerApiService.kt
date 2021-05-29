package com.example.fintonicbeer.service

import com.example.fintonicbeer.model.Beer
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class BeerApiService {
    private val BASE_URL_V2 = "https://api.punkapi.com/v2/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL_V2)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BeerAPI::class.java)

    fun getBeers(): Single<List<Beer>> {
        return api.getBeers()
    }
}