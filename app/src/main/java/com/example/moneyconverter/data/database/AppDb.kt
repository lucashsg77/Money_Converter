package com.example.moneyconverter.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneyconverter.data.database.coinDAO.CoinDAO
import com.example.moneyconverter.data.model.ExchangeResponseValue

@Database(entities = [ExchangeResponseValue::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun exchangeDao(): CoinDAO

    companion object{
        fun instance(context: Context) : AppDb{
            return Room.databaseBuilder(
                context.applicationContext ,
                AppDb::class.java ,
                "coinDb.db"
            )
            .fallbackToDestructiveMigration()
            .build()
        }
    }
}