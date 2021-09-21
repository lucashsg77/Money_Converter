package com.example.moneyconverter.data.model

import com.example.moneyconverter.R
import java.util.*

enum class Coin(val locale: Locale, val icon: Int) {
    USD(Locale.US, R.drawable.ic_us),
    CAD(Locale.CANADA, R.drawable.ic_ca),
    EUR(Locale.GERMANY, R.drawable.ic_european_union),
    KRW(Locale.KOREA, R.drawable.ic_kr),
    BRL(Locale("pt", "BR"), R.drawable.ic_br),
    ARS(Locale("es", "AR"), R.drawable.ic_ar);

    companion object {
        fun findByName(name: String) = values().find { it.name == name } ?: BRL
    }
}