package com.example.gestureapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BankProductItem(
    @StringRes val nameId: Int,
    @DrawableRes val imageId: Int
)
