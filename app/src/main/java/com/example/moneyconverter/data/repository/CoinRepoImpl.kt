package com.example.moneyconverter.data.repository

import com.example.moneyconverter.core.exception.RemoteException
import com.example.moneyconverter.data.database.AppDb
import com.example.moneyconverter.data.model.ErrorResponse
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepoImpl(private val service: AwesomeService, appDb: AppDb) : CoinRepo{

    private val dao = appDb.exchangeDao()

    override suspend fun exchangeValue(coin: String): Flow<ExchangeResponseValue> = flow{
        try{
            val value = service.currencyValue(coin).values.first()
            emit(value)
        }catch (e: HttpException){
            val json = e.response()?.errorBody()?.string()
            val response = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(response.message)
        }
    }

    override suspend fun save(exchangeResponseValue: ExchangeResponseValue) {
        dao.save(exchangeResponseValue)
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.all()
    }
}