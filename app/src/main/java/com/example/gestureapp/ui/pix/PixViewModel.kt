package com.example.gestureapp.ui.pix

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gestureapp.data.DataSource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.max

class PixViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init{
        val actionNumber = 1
        setCurrentCpf(actionNumber)
        setCurrentMoney(actionNumber)
    }

    fun setCurrentCpf(actionNumber: Int){
        _uiState.update { state->
            state.copy(
                currentCpf = DataSource.cpfList[actionNumber - 1]
            )
        }
        Log.i("SET_CURRENT_CPF", "${_uiState.value.currentCpf} actionNumber:$actionNumber")
    }

    fun setCurrentMoney(actionNumber: Int){
        _uiState.update { state->
            state.copy(
                currentMoney = DataSource.moneyList[actionNumber - 1]
            )
        }
        Log.i("SET_CURRENT_MONEY", "${_uiState.value.currentMoney} actionNumber:$actionNumber")
    }

    fun setIsCpfWrong(isWrong: Boolean){
        _uiState.update {state->
            state.copy(
                isCpfWrong = isWrong
            )
        }
    }

    fun setIsMoneyWrong(isWrong: Boolean){
        _uiState.update {state->
            state.copy(
                isMoneyWrong = isWrong
            )
        }
    }

    private fun cleanText(attempt: String): String{
        return attempt
            .replace(".","")
            .replace("-","")
            .trim()
    }

    fun isCpfMatched(attempt: String): Boolean{
        val isEqual = cleanText(attempt) == _uiState.value.currentCpf
        setIsCpfWrong(!isEqual)
        return isEqual
    }

    fun isMoneyMatched(attempt: Double): Boolean{
        Log.i("LOG_MATCHED", "attempt:$attempt currentMoney:${_uiState.value.currentMoney}")
        val isEqual = attempt == _uiState.value.currentMoney
        setIsMoneyWrong(!isEqual)
        return isEqual
    }

}

data class UiState(
    val currentCpf: String = "",
    val currentMoney: Double = 0.00,
    val isCpfWrong: Boolean = false,
    val isMoneyWrong: Boolean = false
)