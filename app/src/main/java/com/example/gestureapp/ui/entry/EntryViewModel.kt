package com.example.gestureapp.ui.entry

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.MutableStateFlow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureapp.data.database.User
import com.example.gestureapp.data.database.UsersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MIN_AGE = 18
const val MAX_AGE = 120

class LoginViewModel(private val usersRepository: UsersRepository): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private var _userUiState = MutableStateFlow(UserUiState())
    var userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()
    lateinit var lastUserUiState: StateFlow<UserUiState>
    val allUsers = usersRepository.getAllUsersStream().map{ AllUsers(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AllUsers()
        )

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

    fun newUiState(){
        _userUiState = MutableStateFlow(UserUiState())
        userUiState = _userUiState.asStateFlow()
    }

    fun setIsTrain(isTrain: Boolean){
        _userUiState.update {state->
            state.copy(
                isTrain = isTrain
            )
        }
    }

    fun setIsFinished(){
        _userUiState.update {state->
            state.copy(
                isFinished = true
            )
        }
        lastUserUiState = _userUiState
        newUiState()
    }

    fun setIsRegistered(){
        _userUiState.update {state->
            state.copy(
                isRegistered = true
            )
        }
    }

    private fun isValidAge(): Boolean{
        val age  =  _userUiState.value.age
        if (age.isNullOrEmpty()) return false
        val ageInt = age.toInt()
        return ageInt in MIN_AGE..MAX_AGE
    }

//    suspend fun getAllUsers(){
//        allUsers = usersRepository.getAllUsersStream()
//    }

    fun saveUser() : Boolean {
        var saved = false
        if (isValidAge()){
            viewModelScope.launch {
                setIsRegistered()
                usersRepository.insertUser((_userUiState.value).toUser())
                saved =  true
            }
        }
        return saved
    }
}

data class UserUiState(
    val id: Int = 0,
    val userName: String = "voce",
    val age: String = "",
    val gender: String = "",
    val isStarted: Boolean = true,
    val isRegistered: Boolean = false,
    val isTrain: Boolean = true,
    val isFinished: Boolean = false
)

data class AllUsers(
    val itemList: List<User> =  listOf()
)
fun UserUiState.toUser(): User = User(
    id = id,
    //userName = userName,
    age = age.toInt(),
    gender = gender.substring(0,1) ,
    isStarted = isStarted,
    isRegistered =  isRegistered,
    isTrain =  isTrain,
    isFinished =  isFinished
)
