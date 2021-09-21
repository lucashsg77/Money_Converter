package com.example.moneyconverter

import android.app.Application
import com.example.moneyconverter.data.di.DataModules
import com.example.moneyconverter.domain.di.DomainModule
import com.example.moneyconverter.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@App)
        }
        DataModules.load()
        DomainModule.load()
        PresentationModule.load()
    }
}