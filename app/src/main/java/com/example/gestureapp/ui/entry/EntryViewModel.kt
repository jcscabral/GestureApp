package com.example.gestureapp.ui.entry

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.database.User
import com.example.gestureapp.data.database.UsersRepository
import com.example.gestureapp.now
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

class EntryViewModel(private val usersRepository: UsersRepository): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
        private const val MIN_AGE = 18
        private const val MAX_AGE = 120
    }

    private val numberOfActions =  DataSource.cpfList.size

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    val allUsers = usersRepository
        .getAllUsersStream()
        .filterNotNull()
        .map { AllUsers(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AllUsers()
        )

    fun setIsLogged(isLogged: Boolean = true){
        _userUiState.update {state->
            state.copy(
                isLogged = isLogged
            )
        }
    }

    fun setIsTest(isTest: Boolean = true){
        _userUiState.update {state->
            state.copy(
                isTest = isTest
            )
        }
        updateUser()
    }

    fun setAge(age: String){
        if(age.length<3){
            _userUiState.update {state->
                state.copy(
                    age = age
                )
            }
        }
    }

    fun setGender(gender: String){
        _userUiState.update {state->
            state.copy(
                gender = gender
            )
        }
    }

    fun setUseOption(useOption: String){
        _userUiState.update {state->
            state.copy(
                useOption = useOption
            )
        }
        setIsTest(DataSource.useOption.last() == _userUiState.value.useOption)
    }

    fun setTestUseOption(){
        _userUiState.update {state->
            state.copy(
                useOption = DataSource.useOption.last()
            )
        }
    }

    private fun setEndTime(){
        _userUiState.update {state->
            state.copy(
                endDateTime = now()
            )
        }
    }

    fun nextAction(): Boolean{
        Log.i("NEXT_ACTION", "Início actionNumber:${_userUiState.value.actionNumber}")
        if (_userUiState.value.actionNumber == numberOfActions){
            setEndTime()
            setIsFinished()
            return false
        }
        else{
            _userUiState.update { state->
                state.copy(
                    actionNumber = _userUiState.value.actionNumber + 1
                )
            }
            AppState.actionNumber =  _userUiState.value.actionNumber
            Log.i("NEXT_ACTION", "Fim actionNumber:${_userUiState.value.actionNumber}")
            return true
        }

    }

    fun setIsFinished(){
        _userUiState.update {state->
            state.copy(
                isFinished = true
            )
        }
        updateUser()
    }

    fun setIsRegistered(){
        _userUiState.update {state->
            state.copy(
                isRegistered = true
            )
        }
        updateUser()
    }

    fun setCurrentUserId(){
        val id = if(allUsers.value.users.isNotEmpty()) allUsers.value.users.first().id else 1
        _userUiState.update {state->
            state.copy(
                id = id
            )
        }
        AppState.id = _userUiState.value.id
    }

    private fun isValidAge(): Boolean{
        val age  =  _userUiState.value.age
        if (age.isNullOrEmpty()) return false
        val ageInt = age.toInt()
        return ageInt in MIN_AGE..MAX_AGE
    }

    private fun isGenreSet(): Boolean{
        val genre = _userUiState.value.gender
        return !genre.isNullOrEmpty()
    }

    fun addUSer(): Boolean{
        if (isValidAge() && isGenreSet()) {
            setIsRegistered()
            insertUser()
            return true
        }
        return false
    }

    private fun insertUser() = runBlocking{
        usersRepository.insertUser((_userUiState.value).toUser())
    }

    private fun updateUser() = runBlocking{
        usersRepository.updateUser((_userUiState.value).toUser())
    }
}

data class UserUiState(
    val id: Int = 0,
    val userName: String = "Cliente",
    val age: String = "",
    val gender: String = "",
    val actionNumber: Int = 1,
    val useOption: String = DataSource.useOption.first(),
    val isRegistered: Boolean = false,
    val isTest: Boolean =  false,
    val isLogged: Boolean = false,
    val isStarted: Boolean = false,
    val isFinished: Boolean = false,
    val initDateTime: String? = now(),
    val endDateTime: String? = null
)

fun UserUiState.toUser(): User = User(
    id = id,
    age = age.toInt(),
    gender = gender.substring(0,1),
    actionNumber = actionNumber,
    isRegistered = isRegistered,
    isTest =  isTest,
    isLogged = isLogged,
    isStarted = isStarted,
    isFinished = isFinished,
    initDateTime = initDateTime,
    endDateTime = endDateTime
)

data class AllUsers(
    val users: List<User> =listOf()
)
