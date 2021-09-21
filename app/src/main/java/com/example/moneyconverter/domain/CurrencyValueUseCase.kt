package com.example.moneyconverter.domain

import com.example.moneyconverter.core.UseCase
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.data.repository.CoinRepo
import kotlinx.coroutines.flow.Flow

class CurrencyValueUseCase(private val repo: CoinRepo) : UseCase<String, ExchangeResponseValue>() {

    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        return repo.exchangeValue(param)
    }

}