package com.example.gestureapp.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gestureapp.data.ACCOUNT_BALANCE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class HomeViewModel(
    savedStateHandle: SavedStateHandle
):ViewModel()
{

    private val _balanceUiState = MutableStateFlow(ACCOUNT_BALANCE)
    val balanceUiState: StateFlow<Double> = _balanceUiState.asStateFlow()
    private var transactionBalance: Double = balanceUiState.value

    fun deduct(deduct: Double){
        transactionBalance = _balanceUiState.value - deduct
    }

    fun confirmTransaction(){
        _balanceUiState.update {
            transactionBalance
        }
    }

    fun reset(){
        _balanceUiState.update {
            ACCOUNT_BALANCE
        }
        transactionBalance = 0.00
    }

}