package com.example.moneyconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.HashMap

typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

@Entity(tableName = "exchangeTb")
data class ExchangeResponseValue(
    @PrimaryKey(autoGenerate = true)
    var id:  Long,
    val codein: String,
    val name: String ,
    val date: String,
    val time: String,
    val bid: Double,
    val fromValue: Double,
    val coin: Coin,
    val finalValue: Double
)
