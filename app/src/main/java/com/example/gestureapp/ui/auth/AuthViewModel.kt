package com.example.gestureapp.ui.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.gestureapp.data.DataSource.Companion.cpfList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.gestureapp.data.PASSWORD
import kotlinx.coroutines.flow.update

class AuthViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel()
{
    private val _uiState = MutableStateFlow(ActionUiState())
    val uiState: StateFlow<ActionUiState> = _uiState.asStateFlow()

    private fun setIsPasswordWrong(isWrong: Boolean){
        _uiState.update {state->
            state.copy(
                isPasswordWrong = isWrong
            )
        }
    }

    fun isMatched(attempt: String): Boolean{
        val isEqual = attempt.trim() == PASSWORD
        setIsPasswordWrong(!isEqual)
        return isEqual
    }

}

data class ActionUiState(
    val isPasswordWrong: Boolean = false
)