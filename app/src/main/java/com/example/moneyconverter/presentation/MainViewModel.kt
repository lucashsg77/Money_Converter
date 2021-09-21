package com.example.moneyconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.domain.CurrencyValueUseCase
import com.example.moneyconverter.domain.SaveCurrencyValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val currencyValueUseCase: CurrencyValueUseCase ,
    private val saveCurrencyValueUseCase: SaveCurrencyValueUseCase
): ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>  = _state

    fun currencyValue(coin: String){
        viewModelScope.launch {
            currencyValueUseCase(coin)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Success(it)
                }
        }
    }

    fun saveCurrencyValue(currencyValue: ExchangeResponseValue){
        viewModelScope.launch {
            saveCurrencyValueUseCase(currencyValue)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value = State.Saved
                }
        }
    }

    sealed class State{
        object Loading : State()
        object Saved: State()

        data class Success(val value: ExchangeResponseValue): State()
        data class Error(val throwable: Throwable): State()
    }

}