package com.example.gestureapp.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.gestureapp.ui.pix.PixConfirmed
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
    Confirmed(R.string.app_confirmed)
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
                canNavigateBack = (navController.previousBackStackEntry != null &&
                        currentScreen != AppScreenEnum.LogIn &&
                        currentScreen != AppScreenEnum.Option &&
                        currentScreen != AppScreenEnum.Home &&
                        currentScreen != AppScreenEnum.Confirmed)
                ,
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
                    useOption = userUiState.useOption,
                    onOptionClicked = {
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

                keyboardViewModel.activeSensor(UserActionEnum.KEYBOARD_LOGIN)
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

                keyboardViewModel.activeSensor(UserActionEnum.KEYBOARD_PIX_MONEY)

                PixMoneyScreen(
                    textField = keyboardUiState.value.textValue,
                    madeAttempt = keyboardUiState.value.madeAttempt,
                    isMoneyWrong = pixUiState.isMoneyWrong,
                    balance = balanceUiSate.value,
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

                keyboardViewModel.activeSensor(UserActionEnum.KEYBOARD_PIX_CPF)

                PixCpfScreen(
                    madeAttempt = keyboardUiState.value.madeAttempt,
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
                            keyboardViewModel.madeAttempt()
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

                keyboardViewModel.activeSensor(UserActionEnum.KEYBOARD_LOGIN)

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
                            navController.navigate(AppScreenEnum.Confirmed.name)
//                            if (userUiState.isFinished){
//                                navController.navigate(AppScreenEnum.Control.name) //TODO new screen
//                            }
//                            else{
//                                navController.navigate(AppScreenEnum.Home.name)
//                            }
                        }
                    },
                    onKeyboardClicked = {
                        if (!keyboardViewModel.onItemClick(it)){ //returns false when "OK" pressed
                            if (authViewModel.isMatched(keyboardUiState.value.textValue)) {
                                keyboardViewModel.clear()
                                homeViewModel.confirmTransaction()
                                navController.navigate(AppScreenEnum.Confirmed.name)
//                                if (userUiState.isFinished){
//                                    navController.navigate(AppScreenEnum.Control.name) //TODO new screen
//                                }
//                                else{
//                                    navController.navigate(AppScreenEnum.Home.name)
//                                }
                            }
                        }
                    },
                    textField = keyboardUiState.value.textValue,
                )
            }
            composable(route = AppScreenEnum.Confirmed.name) {
                PixConfirmed(
                    actionNumber = userUiState.actionNumber,
                    onButtonClick = {
                        if (userUiState.isFinished){
                            navController.navigate(AppScreenEnum.Control.name) //TODO new screen
                        }
                        else{
                            entryViewModel.nextAction()
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    })
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

            if (currentScreen == AppScreenEnum.LogIn) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.meu_banco
                        ),
                        contentDescription = "Seu Banco",
                        tint = Color.Unspecified,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            else{
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(currentScreen.title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = colorScheme.background
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colorScheme.primary //MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
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

@Composable
@Preview
fun Preview(){
    AppScreen()
}