package com.example.gestureapp.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gestureapp.AppViewModelProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.auth.AuthScreen
import com.example.gestureapp.ui.auth.AuthViewModel
import com.example.gestureapp.ui.control.ControlScreen
import com.example.gestureapp.ui.custom.CustomKeyboardViewModel
import com.example.gestureapp.ui.entry.EntryScreen
import com.example.gestureapp.ui.entry.EntryViewModel
import com.example.gestureapp.ui.entry.OptionUseScreen
import com.example.gestureapp.ui.home.HomeScreen
import com.example.gestureapp.ui.home.HomeViewModel
import com.example.gestureapp.ui.pix.PixHomeScreen
import com.example.gestureapp.ui.pix.PixMoneyScreen
import com.example.gestureapp.ui.pix.PixCpfScreen
import com.example.gestureapp.ui.pix.PixViewModel


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
    entryViewModel: EntryViewModel = viewModel(factory = AppViewModelProvider.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory),
    pixViewModel: PixViewModel = viewModel(factory = AppViewModelProvider.Factory),
    keyboardViewModel: CustomKeyboardViewModel = viewModel(factory = AppViewModelProvider.Factory),
    authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreenEnum.valueOf(
        backStackEntry?.destination?.route ?: AppScreenEnum.Control.name
    )
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                showDialog =  showDialog,
                navigateUp = {
                    when(currentScreen){
                        AppScreenEnum.PixReceiver -> keyboardViewModel.setMoneyType()
                        else -> keyboardViewModel.setPasswordType()
                    }
                    keyboardViewModel.disableAllSensors()
                    navController.navigateUp()
                },
            )
        }
    ) { innerPadding ->
        val allUsers by entryViewModel.allUsers.collectAsState()

        val userUiState by entryViewModel.userUiState.collectAsState()
        val keyboardUiState = keyboardViewModel.uiState.collectAsState()
        val authUiState = authViewModel.uiState.collectAsState()
        val balanceUiSate = homeViewModel.balanceUiState.collectAsState()

        val pixUiState by pixViewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = AppScreenEnum.Control.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = AppScreenEnum.Control.name) {
                ControlScreen(
                    currentUserId = userUiState.id,
                    onNewUser = {
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

                keyboardViewModel.disableAllSensors()
                OptionUseScreen(
                    userName = userUiState.userName,
                    useOption = userUiState.useOption,
                    onOptionClicked = {
                        Log.i("onOptionClicked ", it)
                        entryViewModel.setUseOption(it)
                    },
                    onButtonClicked = {
                        keyboardViewModel.clear()
                        navController.navigate(
                            AppScreenEnum.LogIn.name)
                    }
                )
                keyboardViewModel.setPasswordType()
            }
            composable(route = AppScreenEnum.LogIn.name) {

                keyboardViewModel.activeSensor()
                entryViewModel.setId(allUsers)
                AuthScreen(
                    userActionEnum = UserActionEnum.KEYBOARD_LOGIN,
                    text =  "Olá cliente, seja bem-vindo!",
                    textField = keyboardUiState.value.textValue,
                    madeAttempt = keyboardUiState.value.madeAttempt,
                    isPasswordWrong = authUiState.value.isPasswordWrong,
                    onButtonClicked = {
                        keyboardViewModel.madeAttempt()
                        if (authViewModel.isMatched(keyboardUiState.value.textValue)) {
                            entryViewModel.setIsLogged()
                            keyboardViewModel.clear()
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){
                            if (authViewModel.isMatched(keyboardUiState.value.textValue)) {
                                entryViewModel.setIsLogged()
                                keyboardViewModel.clear()
                                navController.navigate(AppScreenEnum.Home.name)
                            }
                        }
                    }
                )
            }
            composable(route = AppScreenEnum.Home.name) {

                keyboardViewModel.disableAllSensors()
                HomeScreen(
                    userName = userUiState.userName,
                    balance = balanceUiSate.value,
                    showDialog = showDialog,
                    navigateExit = {
                        entryViewModel.setTestUseOption()
                        navController.navigate(AppScreenEnum.Option.name)
                    },
                    onButtonClick = {
                        navController.navigate(AppScreenEnum.PixHome.name)
                    }
                )
            }
            composable(route = AppScreenEnum.PixHome.name) {

                keyboardViewModel.disableAllSensors()
                PixHomeScreen(
                    onSendPixButtonClick = {
                        keyboardViewModel.setMoneyType()
                        navController.navigate(AppScreenEnum.PixMoney.name)
                    }
                )
            }
            composable(route = AppScreenEnum.PixMoney.name) {

                keyboardViewModel.activeSensor()

                PixMoneyScreen(
                    madeAttempt = pixUiState.madeAttempt,
                    isMoneyWrong = pixUiState.isMoneyWrong,
                    balance = balanceUiSate.value,
                    textField = keyboardUiState.value.textValue,
                    onButtonClicked = {
                        keyboardViewModel.madeAttempt()
                        val deduct = keyboardViewModel.getTextAsDouble()
                        if (pixViewModel.isMoneyMatched(deduct)) {
                            homeViewModel.deduct(deduct)
                            keyboardViewModel.setCpfType()
                            navController.navigate(AppScreenEnum.PixReceiver.name)
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){
                            val deduct = keyboardViewModel.getTextAsDouble()
                            if (pixViewModel.isMoneyMatched(deduct)) {
                                homeViewModel.deduct(deduct)
                                keyboardViewModel.setCpfType()
                                navController.navigate(AppScreenEnum.PixReceiver.name)
                            }
                        }
                    },
                )
            }
            composable(route = AppScreenEnum.PixReceiver.name) {

                keyboardViewModel.activeSensor()

                PixCpfScreen(
                    madeAttempt = pixUiState.madeAttempt,
                    isCpfWrong = pixUiState.isCpfWrong,
                    onButtonClicked = {
                        keyboardViewModel.madeAttempt()
                        if (pixViewModel.isCpfMatched(keyboardUiState.value.textValue)) {
                            keyboardViewModel.setPasswordType()
                            navController.navigate(AppScreenEnum.Auth.name)
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){ //returns false when "OK" pressed
                            pixViewModel.madeAttempt(true)
                            if (pixViewModel.isCpfMatched(keyboardUiState.value.textValue)) {
                                keyboardViewModel.setPasswordType()
                                navController.navigate(AppScreenEnum.Auth.name)
                            }
                        }
                    },
                    textField = keyboardUiState.value.textValue,
                )
            }
            composable(route = AppScreenEnum.Auth.name) {

                keyboardViewModel.activeSensor()

                AuthScreen(
                    userActionEnum = UserActionEnum.KEYBOARD_AUTH,
                    text = "Autenticação da senha",
                    madeAttempt = keyboardUiState.value.madeAttempt,
                    isPasswordWrong = authUiState.value.isPasswordWrong,
                    onButtonClicked = {
                        keyboardViewModel.madeAttempt()
                        if (authViewModel.isMatched(keyboardUiState.value.textValue)) {
                            keyboardViewModel.clear()
                            homeViewModel.confirmTransaction()
                            entryViewModel.nextAction()
                            if (userUiState.isFinished){
                                navController.navigate(AppScreenEnum.Control.name) //TODO new screen
                            }
                            else{
                                navController.navigate(AppScreenEnum.Home.name)
                            }
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){ //returns false when "OK" pressed
                            if (authViewModel.isMatched(keyboardUiState.value.textValue)) {
                                keyboardViewModel.clear()
                                homeViewModel.confirmTransaction()
                                entryViewModel.nextAction()
                                if (userUiState.isFinished){
                                    navController.navigate(AppScreenEnum.Control.name) //TODO new screen
                                }
                                else{
                                    navController.navigate(AppScreenEnum.Home.name)
                                }
                            }
                        }
                    },
                    textField = keyboardUiState.value.textValue,
                )
            }
        }
    }
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppBar(
    currentScreen: AppScreenEnum,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    showDialog: MutableState<Boolean>,
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
                        //imageVector = Icons.Filled.ArrowBack,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Voltar"
                    )
                }
            }
        },
        actions = {
            if(currentScreen == AppScreenEnum.Home){
                Button(onClick = {
                    showDialog.value = true
                }
                ) {
                    Text(text = "Sair")
                }
            }
        }
    )

}