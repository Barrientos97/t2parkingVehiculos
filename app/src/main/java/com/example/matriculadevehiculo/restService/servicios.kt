package com.example.matriculadevehiculo.restService

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class servicios() {

    val BASE_URL ="https://billing.t2voice.com/app/"
    var retrofit: Retrofit = Retrofit.Builder()

        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun zone():ApiService{
        return retrofit.create(ApiService::class.java)
    }
}