package com.example.moneyconverter.domain.di

import com.example.moneyconverter.domain.CurrencyValueUseCase
import com.example.moneyconverter.domain.ListCurrencyUseCase
import com.example.moneyconverter.domain.SaveCurrencyValueUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load(){
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory{
                CurrencyValueUseCase(get())
            }
            factory{
                SaveCurrencyValueUseCase(get())
            }
            factory{
                ListCurrencyUseCase(get())
            }
        }
    }
}