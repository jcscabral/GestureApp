package com.example.gestureapp.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val ACCOUNT_BALANCE =  100000.00
class HomeViewModel(
    savedStateHandle: SavedStateHandle
):ViewModel()
{

    private val _balanceUiState = MutableStateFlow(ACCOUNT_BALANCE)
    val balanceUiState: StateFlow<Double> = _balanceUiState.asStateFlow()
    private var transactionBalance: Double = balanceUiState.value

    fun minus(deduct: Double){
        Log.i("FIRED", "minus: _balanceUiState:$_balanceUiState")
        transactionBalance = _balanceUiState.value - deduct
        Log.i("FIRED", "transactionBalance:$transactionBalance")
    }

    fun confirm(){
        _balanceUiState.update {
            transactionBalance
        }
        Log.i("FIRED", "confirm: _balanceUiState:$_balanceUiState")
    }

}