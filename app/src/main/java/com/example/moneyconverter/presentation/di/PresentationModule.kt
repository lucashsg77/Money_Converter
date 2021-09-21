package com.example.moneyconverter.presentation.di

import com.example.moneyconverter.presentation.HistoryViewModel
import com.example.moneyconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModule {

    fun load(){
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
             viewModel {
                 MainViewModel(get(), get())
             }
            viewModel{
                HistoryViewModel(get())
            }
        }
    }
}