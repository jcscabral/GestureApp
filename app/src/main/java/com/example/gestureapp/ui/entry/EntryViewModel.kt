package com.example.gestureapp.ui.entry

import kotlinx.coroutines.flow.MutableStateFlow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.PASSWORD
import com.example.gestureapp.data.database.User
import com.example.gestureapp.data.database.UsersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking

const val MIN_AGE = 18
const val MAX_AGE = 120

class EntryViewModel(private val usersRepository: UsersRepository): ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    val allUsers = usersRepository
        .getAllUsersStream()
        .filterNotNull()
        .map { AllUsers(it) }
        .stateIn( // flow to stateflow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            //started = SharingStarted.Eagerly,
            initialValue = AllUsers()
        )

//    fun newUiState(){
//        _userUiState = MutableStateFlow(UserUiState())
//        userUiState = _userUiState.asStateFlow()
//    }

    fun madeAttempt(made: Boolean){
        _userUiState.update {state->
            state.copy(
                madeAttempt = made
            )
        }
    }

    fun setIsPasswordWrong(isWrong: Boolean){
        _userUiState.update {state->
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

    fun setIsLogged(isLogged: Boolean){
        _userUiState.update {state->
            state.copy(
                isLogged = isLogged
            )
        }
    }

    fun setSession(){
        _userUiState.update {state->
            state.copy(
                session = _userUiState.value.session + 1
            )
        }
        AppState.sectionId = _userUiState.value.session
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
    }

    fun setTestUseOption(){
        _userUiState.update {state->
            state.copy(
                useOption = DataSource.useOption.last()
            )
        }
    }

    fun setIsFinished(){
        _userUiState.update {state->
            state.copy(
                isFinished = true
            )
        }
    }

    fun setIsRegistered(){
        _userUiState.update {state->
            state.copy(
                isRegistered = true
            )
        }
    }

    fun setId(allUsers: AllUsers){
        val id = if(allUsers.users.isNotEmpty()) allUsers.users.first().id else 1
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

    fun isGenreSet(): Boolean{
        val genre = _userUiState.value.gender
        return !genre.isNullOrEmpty()
    }

    fun addUSer(): Boolean{
        if (isValidAge() && isGenreSet()) {
            setIsRegistered()
            saveUser()
            return true
        }
        return false
    }


//    private fun getLastUser(){
//           viewModelScope.launch {
//               val currenUsers  = allUsers.value.users
//           }
//    }

    private fun saveUser() = runBlocking{
        usersRepository.insertUser((_userUiState.value).toUser())
        //val currentUsers  = allUsers.value.users
        //setId(currentUsers.first().id)

    }
}

data class UserUiState(
    val id: Int = 0,
    val userName: String = "cliente",
    val age: String = "",
    val gender: String = "",
    val session: Int = 0,
    val useOption: String = DataSource.useOption.first(),
    val madeAttempt: Boolean = false ,
    val isPasswordWrong: Boolean = false,
    val isRegistered: Boolean = false,
    val isLogged: Boolean = false,
    val isStarted: Boolean = false,
    val isFinished: Boolean = false
)

fun UserUiState.toUser(): User = User(
    id = id,
    age = age.toInt(),
    gender = gender.substring(0,1) ,
    session = session,
    isRegistered = isRegistered,
    isTrain =  useOption == DataSource.useOption.first(),
    isLogged = isLogged,
    isStarted = isStarted,
    isFinished =  isFinished
)
data class AllUsers(
    //val users: List<User> =listOf<User>().sortedByDescending { it.id }
    val users: List<User> =listOf()
)
