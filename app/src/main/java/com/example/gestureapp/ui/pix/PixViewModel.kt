package com.example.gestureapp.ui.pix

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gestureapp.data.AppState
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
        setCurrentCpf()
        setCurrentMoney()
    }

    private fun setCurrentCpf(){
        val currentId = max(AppState.sectionId -1, 0)
        _uiState.update { state->
            state.copy(
                currentCpf = DataSource.cpfList[currentId]
            )
        }
    }

    private fun setCurrentMoney(){
        val currentId = max(AppState.sectionId -1, 0)
        _uiState.update { state->
            state.copy(
                currentMoney = DataSource.moneyList[currentId]
            )
        }
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

    fun madeAttempt(made: Boolean){
        _uiState.update {state->
            state.copy(
                madeAttempt = made
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
        setCurrentCpf()
        val isEqual = cleanText(attempt) == _uiState.value.currentCpf
        setIsCpfWrong(!isEqual)
        return isEqual
    }

    fun isMoneyMatched(attempt: Double): Boolean{
        setCurrentMoney()
        val isEqual = attempt == _uiState.value.currentMoney
        setIsMoneyWrong(!isEqual)
        return isEqual
    }

}

data class UiState(
    val currentCpf: String = "",
    val currentMoney: Double = 0.00,
    val madeAttempt: Boolean = false,
    val isCpfWrong: Boolean = false,
    val isMoneyWrong: Boolean = false
)