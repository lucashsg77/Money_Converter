package com.example.moneyconverter.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.moneyconverter.core.extensions.createDialog
import com.example.moneyconverter.core.extensions.createProgressDialog
import com.example.moneyconverter.databinding.ActivityHistoryBinding
import com.example.moneyconverter.presentation.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDateTime

class HistoryActivity: AppCompatActivity() {

    private val adapter by lazy { HistoryAdapter()}
    private val binding by lazy { ActivityHistoryBinding.inflate(layoutInflater)}
    private val viewModel by viewModel<HistoryViewModel>()
    private val dialog by lazy { createProgressDialog()}

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.historyRv.adapter = adapter
        binding.historyRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setUpObserver()
        lifecycle.addObserver(viewModel)
    }

    private fun setUpObserver() {
        viewModel.state.observe(this){
            when(it){
                HistoryViewModel.State.Loading -> dialog.show()
                is HistoryViewModel.State.Error -> {
                    dialog.dismiss()
                    createDialog{
                        setMessage(it.throwable.message)
                    }.show()
                }
                is HistoryViewModel.State.Success -> {
                    dialog.dismiss()
                    adapter.submitList(it.list)
                }
            }
        }
    }
}