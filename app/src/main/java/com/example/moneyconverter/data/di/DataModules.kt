package com.example.moneyconverter.data.di

import android.util.Log
import com.example.moneyconverter.data.database.AppDb
import com.example.moneyconverter.data.repository.CoinRepo
import com.example.moneyconverter.data.repository.CoinRepoImpl
import com.example.moneyconverter.data.services.AwesomeService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {

    private const val HTTP_TAG = "OkHttp"

    fun load(){
        loadKoinModules(networkModule() + repoModule() + dbModule())
    }

    private fun networkModule(): Module {
        return module{
            single{
                val interceptor = HttpLoggingInterceptor{
                    Log.e(HTTP_TAG, ": $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
            single{
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single{
                createService<AwesomeService>(get(), get())
            }
            factory{

            }
        }
    }

    private fun repoModule(): Module {
        return module{
            single<CoinRepo>{
                CoinRepoImpl(get(), get())
            }
        }
    }

    private fun dbModule(): Module {
        return module{
            single{
                AppDb.instance(androidApplication())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): T{
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br")
            .client(client)
            .addConverterFactory(gsonConverterFactory).build()
            .create(T::class.java)
    }

}