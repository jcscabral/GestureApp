package com.example.gestureapp.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gestureapp.AppViewModelProvider
import com.example.gestureapp.R
import com.example.gestureapp.ui.control.ControlScreen
import com.example.gestureapp.ui.entry.EntryScreen
import com.example.gestureapp.ui.entry.EntryViewModel
import com.example.gestureapp.ui.home.HomeScreen


enum class AppScreenEnum(@StringRes val title: Int){
    Control(title = R.string.app_controle),
    Entry(title = R.string.app_acesso),
    Home(R.string.app_mensagem_entrada),
    Auth(R.string.app_autenticacao),
    Pix(R.string.app_pix),    
    Other(R.string.app_fora)
}
@Composable
fun AppScreen(
    loginViewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: AppScreenEnum.Control.name
    )
    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val userUiState by loginViewModel.userUiState.collectAsState()
        val allUsers by loginViewModel.allUsers.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreenEnum.Control.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = AppScreenEnum.Control.name) {
                ControlScreen(
                    currentUserId = userUiState.id,
                    onNewUser = {
                        loginViewModel.newUiState()
                        navController.navigate(AppScreenEnum.Entry.name)
                    },
                    allUsers =  allUsers.users,
                    modifier
                )
            }
            composable(route = AppScreenEnum.Entry.name) {
                EntryScreen(
                    //isStarted =  userUiState.isStarted,
                    id = userUiState.id,
                    userName = userUiState.userName,
                    age = userUiState.age,
                    gender = userUiState.gender,
                    isPasswordWrong = userUiState.isPasswordWrong,
                    isRegistered = userUiState.isRegistered,
                    useOption = userUiState.useOption,
                    onAgeValueChange = {
                        loginViewModel.setAge(it)
                    },
                    onGenderClick = {
                        loginViewModel.setGender(it)
                    },
                    onUserRegistered = {
                        loginViewModel.saveUser()
                        if (userUiState.isRegistered){
                            loginViewModel.updateId()
                            navController.navigate(AppScreenEnum.Control.name)
                        }
                    },
                    onTrainClicked = {
                        loginViewModel.setUseOption(it)
                    },
                    onLoginClicked = {
                        if (loginViewModel.isMatched(it)){
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    }
                )
            }
//            composable(route = AppScreenEnum.Home.name) {
//                HomeScreen(
//                    swipeSensorManager = ,
//                    buttonSensorManager = ,
//                    keyboardSensorManager =
//                )
//            }

        }


    }

}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppBar(
    currentScreen: AppScreenEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer
//        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Voltar" //stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}