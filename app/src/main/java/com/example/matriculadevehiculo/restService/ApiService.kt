package com.example.matriculadevehiculo.restService


import com.example.matriculadevehiculo.modelos.ContenedorZone
import com.example.matriculadevehiculo.modelos.Country
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("getFullBillingBlockDay")
    fun getZone():Call<ContenedorZone>
}

