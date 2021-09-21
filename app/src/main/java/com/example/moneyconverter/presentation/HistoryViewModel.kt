package com.example.moneyconverter.presentation

import androidx.lifecycle.*
import com.example.moneyconverter.data.model.ExchangeResponseValue
import com.example.moneyconverter.domain.ListCurrencyUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel(private val listCurrencyUseCase: ListCurrencyUseCase): ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun exchange(){
        viewModelScope.launch {
            listCurrencyUseCase()
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

    sealed class State{
        object Loading : State()

        data class Success(val list: List<ExchangeResponseValue>): State()
        data class Error(val throwable: Throwable): State()
    }

}