package com.example.moneyconverter.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.moneyconverter.data.model.ExchangeResponseValue

class DiffCallback: DiffUtil.ItemCallback<ExchangeResponseValue>() {
    override fun areItemsTheSame(
        oldItem: ExchangeResponseValue ,
        newItem: ExchangeResponseValue
    ): Boolean = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: ExchangeResponseValue ,
        newItem: ExchangeResponseValue
    ): Boolean = oldItem.name == newItem.name
}