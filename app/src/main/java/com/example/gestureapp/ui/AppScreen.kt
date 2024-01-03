package com.example.gestureapp.ui

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
import com.example.gestureapp.ui.custom.CustomKeyboard
import com.example.gestureapp.ui.custom.CustomKeyboardViewModel
import com.example.gestureapp.ui.entry.EntryScreen
import com.example.gestureapp.ui.entry.EntryViewModel
import com.example.gestureapp.ui.entry.LogIn
import com.example.gestureapp.ui.home.HomeScreen
import com.example.gestureapp.ui.pix.PixHomeScreen
import com.example.gestureapp.ui.pix.PixMoneyScreen
import com.example.gestureapp.ui.pix.PixReceiverScreen


enum class AppScreenEnum(@StringRes val title: Int){
    Control(title = R.string.app_controle),
    SignIn(title = R.string.app_signin),
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
    keyboardViewModel: CustomKeyboardViewModel = CustomKeyboardViewModel(),
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
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val userUiState by entryViewModel.userUiState.collectAsState()
        val allUsers by entryViewModel.allUsers.collectAsState()

        val textUiState = keyboardViewModel.textUiState

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
                    //isStarted =  userUiState.isStarted,
                    //id = userUiState.id,
                    userName = userUiState.userName,
                    age = userUiState.age,
                    gender = userUiState.gender,
                    //isPasswordWrong = userUiState.isPasswordWrong,
                    isRegistered = userUiState.isRegistered,
                    //useOption = userUiState.useOption,
                    onAgeValueChange = {
                        entryViewModel.setAge(it)
                    },
                    onGenderClick = {
                        entryViewModel.setGender(it)
                    },
                    onUserRegistered = {
                        if(entryViewModel.addUSer()){
                            navController.navigate(AppScreenEnum.LogIn.name)
                        }
                    },
//                    onTrainClicked = {
//                        entryViewModel.setUseOption(it)
//                    },
//                    onLoginClicked = {
//                        if (entryViewModel.isMatched(it)){
//                            navController.navigate(AppScreenEnum.Home.name)
//                        }
                    //}
                )
            }
            composable(route = AppScreenEnum.LogIn.name) {
                entryViewModel.setId(allUsers)
                LogIn(
                    id = userUiState.id,
                    userName = userUiState.userName,
                    isPasswordWrong = userUiState.isPasswordWrong,
                    useOption = userUiState.useOption,
                    onTrainClicked = {
                        entryViewModel.setUseOption(it)
                    },
                    onLoginClick = {
                        if (entryViewModel.isMatched(it)){
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    }
                )
            }
            composable(route = AppScreenEnum.Home.name) {
                HomeScreen(
                    id = userUiState.id,
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
                        navController.navigate(AppScreenEnum.PixMoney.name)
                    }
                )
            }
            composable(route = AppScreenEnum.PixMoney.name) {
                keyboardViewModel.setMoneyType()
                PixMoneyScreen(
                    textValue = textUiState.textValue,
                    showSheet = textUiState.showSheet,
                    onItemClick = {
                        if (!keyboardViewModel.onItemClick(it)){
                            navController.navigate(AppScreenEnum.PixReceiver.name)
                        }
                    },
                    onDismissRequest = {
                        keyboardViewModel.onDismissRequest()
                    }
                )
            }
            composable(route = AppScreenEnum.PixReceiver.name) {
                keyboardViewModel.setCpfType()
                PixReceiverScreen(
                    textValue = textUiState.textValue,
                    showSheet = textUiState.showSheet,
                    onItemClick = {
                        if (!keyboardViewModel.onItemClick(it)){
                            navController.navigate(AppScreenEnum.Auth.name)
                        }
                    },
                    onDismissRequest = {
                        keyboardViewModel.onDismissRequest()
                    }
                )
            }
            composable(route = AppScreenEnum.Auth.name) {
                keyboardViewModel.setPasswordType()
                AuthScreen(
                    textValue = textUiState.textValue,
                    showSheet = textUiState.showSheet,
                    onItemClick = {
                        if (!keyboardViewModel.onItemClick(it)){
                            navController.navigate(AppScreenEnum.Home.name)
                        }
                    },
                    onDismissRequest = {
                        keyboardViewModel.onDismissRequest()
                    }
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