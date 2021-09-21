package com.example.moneyconverter.data.services

import com.example.moneyconverter.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {
    @GET("/json/last/{coins}")
    suspend fun currencyValue(@Path("coins") coins: String) : ExchangeResponse
}