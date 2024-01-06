package com.example.gestureapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gestureapp.ui.custom.CustomKeyboardViewModel
import com.example.gestureapp.ui.entry.EntryViewModel
import com.example.gestureapp.ui.home.HomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel

        initializer {
            EntryViewModel(gestureApplication()
                .container.usersRepository)
        }

        initializer {
            HomeViewModel(
                this.createSavedStateHandle()
            )
        }

        initializer {
            CustomKeyboardViewModel(
                this.createSavedStateHandle()
            )
        }

    }
}

fun CreationExtras.gestureApplication(): MainApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)