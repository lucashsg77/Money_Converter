package com.example.moneyconverter.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moneyconverter.R
import com.example.moneyconverter.core.extensions.formatCurrency
import com.example.moneyconverter.data.model.Coin
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.databinding.ItemHistoryBinding
import org.koin.android.ext.koin.androidContext

class HistoryAdapter: ListAdapter<ExchangeResponseValue, HistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): HistoryAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder , position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(item: ExchangeResponseValue){
            val locale = Coin.findByName(item.codein).locale
            val context = binding.root.context
            binding.nameTv.text = item.name
            binding.bidTv.text =  context.getString(R.string.bid) + " ${item.bid.formatCurrency(locale)}"
            binding.dateTv.text = item.date
            binding.timeTv.text = item.time
            binding.fromTv.text = context.getString(R.string.from) + " ${item.fromValue.formatCurrency(item.coin.locale)}"
            binding.valueTv.text = context.getString(R.string.finalValue) +  " ${item.finalValue.formatCurrency(locale)}"
        }

    }
}