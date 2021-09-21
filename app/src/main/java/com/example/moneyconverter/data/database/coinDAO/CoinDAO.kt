package com.example.moneyconverter.data.database.coinDAO

import androidx.room.*
import com.example.moneyconverter.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDAO {

    @Query("SELECT * FROM exchangeTb")
    fun all(): Flow<List<ExchangeResponseValue>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entity: ExchangeResponseValue)
}