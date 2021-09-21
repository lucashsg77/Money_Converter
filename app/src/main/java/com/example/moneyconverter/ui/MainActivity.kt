package com.example.moneyconverter.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.example.moneyconverter.R
import com.example.moneyconverter.core.extensions.*
import com.example.moneyconverter.data.model.Coin
import com.example.moneyconverter.databinding.ActivityMainBinding
import com.example.moneyconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private val viewModel by  viewModel<MainViewModel>()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindAdapter()
        bindListeners()
        setUpObserver()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.historyAction)
            startActivity(Intent(this, HistoryActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    private fun bindListeners() {
        binding.valueTil.editText?.doAfterTextChanged{
            binding.convertBtn.isEnabled = it != null && it.toString().isNotEmpty()
            binding.saveBtn.isEnabled = false
        }
        binding.convertBtn.setOnClickListener {
            it.hideSoftKeyboard()
            viewModel.currencyValue("${binding.fromTil.text}-${binding.toTil.text}")
        }
        binding.saveBtn.setOnClickListener {
            val value = viewModel.state.value
            val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.now()
            } else {
                null
            }
            (value as? MainViewModel.State.Success)?.let{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && date != null) {
                    viewModel.saveCurrencyValue(it.value.copy(
                        bid = it.value.bid,
                        date = "${date.dayOfMonth}/${date.monthValue}/${date.year}",
                        time = "${date.hour}:${date.minute}:${date.second}",
                        fromValue = binding.valueTil.text.toDouble(),
                        coin = Coin.findByName(binding.fromTv.text.toString()),
                        finalValue = it.value.bid * binding.valueTil.text.toDouble()
                    ))
                }
            }
        }

        binding.fromTv.setOnItemClickListener { _, _, _, _->
            val fromCoin = Coin.values().find { currency ->
                currency.name == binding.fromTil.text
            }
            if (fromCoin != null) {
                binding.fromIv.setImageDrawable(ContextCompat.getDrawable(this, fromCoin.icon))
            }
        }
        binding.toTv.setOnItemClickListener { _, _, _, _ ->
            val toCoin = Coin.values().find { currency ->
                currency.name == binding.toTil.text
            }
            if (toCoin != null) {
                binding.toIv.setImageDrawable(ContextCompat.getDrawable(this,toCoin.icon))
            }
        }
    }

    private fun bindAdapter() {
        val list = Coin.values()
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, list)
        binding.fromTv.setAdapter(adapter)
        binding.toTv.setAdapter(adapter)
        binding.fromTv.setText(Coin.BRL.name, false)
        binding.toTv.setText(Coin.USD.name, false)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObserver(){
        viewModel.state.observe(this){
            when (it){
                MainViewModel.State.Loading -> dialog.show()
                is MainViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage(it.throwable.message)
                    }.show()
                }
                is MainViewModel.State.Success ->{
                    dialog.dismiss()
                    binding.saveBtn.isEnabled = true
                    val toCoin = Coin.values().find { currency ->
                        currency.name == binding.toTil.text
                    }
                    val fromCoin = Coin.values().find { currency ->
                        currency.name == binding.fromTil.text
                    }
                    if (toCoin != null && fromCoin != null) {

                        binding.resultTv.text =
                            getString(R.string.finalValue) + " ${ (it.value.bid * binding.valueTil.text.toDouble()).formatCurrency(toCoin.locale) }"
                        binding.bidTv.text =
                            "${(1.00).formatCurrency(fromCoin.locale)} = ${ it.value.bid.formatCurrency(toCoin.locale) }"
                        binding.resultTv.visibility = View.VISIBLE
                        binding.bidTv.visibility = View.VISIBLE
                    }
                }
                is MainViewModel.State.Saved -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage(getString(R.string.saveSuccess))
                    }.show()
                }
                else -> Log.e("TAG", "Error")
            }
        }
    }
}