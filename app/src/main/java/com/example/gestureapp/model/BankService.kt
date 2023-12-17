package com.example.gestureapp.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BankService(
    @StringRes val nameId: Int,
    val imageIcon: ImageVector
)
