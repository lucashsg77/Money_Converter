package com.example.moneyconverter.data.repository

import com.example.moneyconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

interface CoinRepo {

    suspend fun exchangeValue(coin: String): Flow<ExchangeResponseValue>

    suspend fun save(exchangeResponseValue: ExchangeResponseValue)

    fun list(): Flow<List<ExchangeResponseValue>>

}