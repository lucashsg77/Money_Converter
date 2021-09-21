package com.example.moneyconverter.domain

import com.example.moneyconverter.core.UseCase
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.data.repository.CoinRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveCurrencyValueUseCase(private val repository: CoinRepo) : UseCase.NoSource<ExchangeResponseValue>() {
    override suspend fun execute(param: ExchangeResponseValue): Flow<Unit> {
        return flow{
            repository.save(param)
            emit(Unit)
        }

    }
}