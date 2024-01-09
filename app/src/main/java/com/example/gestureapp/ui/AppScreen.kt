package com.example.gestureapp.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.gestureapp.model.ComponentSensorManager
import com.example.gestureapp.ui.auth.AuthScreen
import com.example.gestureapp.ui.control.ControlScreen
import com.example.gestureapp.ui.custom.CustomKeyboardViewModel
import com.example.gestureapp.ui.entry.EntryScreen
import com.example.gestureapp.ui.entry.EntryViewModel
import com.example.gestureapp.ui.entry.OptionUseScreen
import com.example.gestureapp.ui.home.HomeScreen
import com.example.gestureapp.ui.home.HomeViewModel
import com.example.gestureapp.ui.pix.PixHomeScreen
import com.example.gestureapp.ui.pix.PixMoneyScreen
import com.example.gestureapp.ui.pix.PixReceiverScreen


enum class AppScreenEnum(@StringRes val title: Int){
    Control(title = R.string.app_controle),
    SignIn(title = R.string.app_signin),
    Option(title = R.string.app_opcao_uso),
    LogIn(title = R.string.app_login),
    Home(R.string.app_mensagem_entrada),
    PixHome(R.string.app_pix_home),
    PixMoney(R.string.app_pix_money),
    PixReceiver(R.string.app_pix_receiver),
    Auth(R.string.app_autenticacao),
    Other(R.string.app_fora)
}
@Composable
fun AppScreen(
    swipeSensorManager: ComponentSensorManager,
    buttonSensorManager: ComponentSensorManager,
    keyboardSensorManager: ComponentSensorManager,
    entryViewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    keyboardViewModel: CustomKeyboardViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    //val coroutineScope = rememberCoroutineScope()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: AppScreenEnum.Control.name
    )
    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onHomeReturn = { navController.navigate(AppScreenEnum.Option.name)}
            )
        }
    ) { innerPadding ->
        val userUiState by entryViewModel.userUiState.collectAsState()
        val allUsers by entryViewModel.allUsers.collectAsState()

        val keyboardUiState = keyboardViewModel.uiState.collectAsState()
        val balanceUiSate = homeViewModel.balanceUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreenEnum.Control.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = AppScreenEnum.Control.name) {
                ControlScreen(
                    currentUserId = userUiState.id,
                    onNewUser = {
                        entryViewModel.newUiState()
                        navController.navigate(
                            AppScreenEnum.SignIn.name
                        )
                    },
                    allUsers =  allUsers.users,
                    modifier
                )
            }
            composable(route = AppScreenEnum.SignIn.name) {
                EntryScreen(
                    userName = userUiState.userName,
                    age = userUiState.age,
                    gender = userUiState.gender,
                    onAgeValueChange = {
                        entryViewModel.setAge(it)
                    },
                    onGenderClick = {
                        entryViewModel.setGender(it)
                    },
                    onButtonClicked = {
                        if(entryViewModel.addUSer()){
                            keyboardViewModel.setPasswordType()
                            navController.navigate(AppScreenEnum.Option.name)
                            true
                        }
                        else{
                            false
                        }
                    }
                )
            }
            composable(route = AppScreenEnum.Option.name){
                OptionUseScreen(
                    userName = userUiState.userName,
                    useOption = userUiState.useOption,
                    onOptionClicked = {
                        Log.i("onOptionClicked ", it)
                        entryViewModel.setUseOption(it)
                    },
                    onButtonClicked = {
                        navController.navigate(
                            AppScreenEnum.LogIn.name)
                    }
                )
            }
            composable(route = AppScreenEnum.LogIn.name) {
                entryViewModel.setId(allUsers)
                AuthScreen(
                    //id = userUiState.id,
                    //userName = userUiState.userName,
                    madeAttempt = userUiState.madeAttempt,
                    isPasswordWrong = userUiState.isPasswordWrong,
                    onButtonClicked = {
                        entryViewModel.madeAttempt(true)
                        if (entryViewModel.isMatched(keyboardUiState.value.textValue)) {
                            entryViewModel.setIsLogged(true)
                            entryViewModel.setSession()
                            keyboardViewModel.clear()
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){ //returns false when "OK" pressed
                            entryViewModel.madeAttempt(true)
                            if (entryViewModel.isMatched(keyboardUiState.value.textValue)) {
                                entryViewModel.setIsLogged(true)
                                entryViewModel.setSession()
                                keyboardViewModel.clear()
                                navController.navigate(AppScreenEnum.Home.name)
                            }
                        }
                        else{
                            entryViewModel.madeAttempt(false)
                        }
                    },
                    textField = keyboardUiState.value.textValue,
//                    showSheet = keyboardUiState.value.showSheet,
//                    onDismissRequest = {
//                        keyboardViewModel.onDismissRequest()
//                    }
                )
            }
            composable(route = AppScreenEnum.Home.name) {
                HomeScreen(
                    id = userUiState.id,
                    session = userUiState.session,
                    balance = balanceUiSate.value,
                    swipeSensorManager = swipeSensorManager,
                    buttonSensorManager = buttonSensorManager,
                    keyboardSensorManager = keyboardSensorManager,
                    onButtonClick = {
                        navController.navigate(AppScreenEnum.PixHome.name)
                    }
                )
            }
            composable(route = AppScreenEnum.PixHome.name) {
                PixHomeScreen(
                    onSendPixButtonClick = {
                        keyboardViewModel.setMoneyType()
                        navController.navigate(AppScreenEnum.PixMoney.name)
                    }
                )
            }
            composable(route = AppScreenEnum.PixMoney.name) {
                PixMoneyScreen(
                    balance = balanceUiSate.value,
                    textValue = keyboardUiState.value.textValue,
                    showSheet = keyboardUiState.value.showSheet,
                    onItemClick = {
                        if (!keyboardViewModel.onItemClick(it)){
                            homeViewModel.minus(keyboardViewModel.getTextAsDouble())
                            keyboardViewModel.setCpfType()
                            navController.navigate(AppScreenEnum.PixReceiver.name)
                        }
                    },
                    onDismissRequest = {
                        keyboardViewModel.onDismissRequest()
                    }
                )
            }
            composable(route = AppScreenEnum.PixReceiver.name) {
                PixReceiverScreen(
                    textValue = keyboardUiState.value.textValue,
                    showSheet = keyboardUiState.value.showSheet,
                    onItemClick = {
                        if (!keyboardViewModel.onItemClick(it)){
                            entryViewModel.madeAttempt(true)
                            if (entryViewModel.isMatched(keyboardUiState.value.textValue)) {
                                navController.navigate(AppScreenEnum.Home.name)
                            }
                        }
                        else{
                            entryViewModel.madeAttempt(false)
                        }
                    },
                    onDismissRequest = {
                        keyboardViewModel.onDismissRequest()
                    }
                )
            }
//            composable(route = AppScreenEnum.Auth.name) {
//
//                AuthScreen(
//                    textValue = textUiState.value.textValue,
//                    showSheet = textUiState.value.showSheet,
//                    onItemClick = {
//                        if (!keyboardViewModel.onItemClick(it)){
//                            navController.navigate(AppScreenEnum.Home.name)
//                        }
//                    },
//                    onDismissRequest = {
//                        keyboardViewModel.onDismissRequest()
//                    }
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
    onHomeReturn: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(stringResource(currentScreen.title),
                color = colorScheme.background)
                },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorScheme.primary //MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack &&
                currentScreen != AppScreenEnum.Home) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }
        },
        actions = {
            if(currentScreen == AppScreenEnum.Home){
                Button(onClick = {}) {
                    Text(text = "Sair")
                }
            }
        }
    )
}