package com.example.gestureapp.ui.home

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

    fun minus(deduct: Double){
        _balanceUiState.update { state ->
            state - deduct
        }
    }

}