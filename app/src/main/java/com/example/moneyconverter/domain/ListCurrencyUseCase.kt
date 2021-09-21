package com.example.moneyconverter.domain

import com.example.moneyconverter.core.UseCase
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.data.repository.CoinRepo
import kotlinx.coroutines.flow.Flow

class ListCurrencyUseCase(private val repository: CoinRepo) : UseCase.NoParam<List<ExchangeResponseValue>>(){
    override suspend fun execute(): Flow<List<ExchangeResponseValue>> {
     return repository.list()
    }
}