package com.example.gestureapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.ShoppingCart
import com.example.gestureapp.R
import com.example.gestureapp.model.BankProductItem


class DataSource {
    fun load(): List<BankProductItem>{
        return listOf(
            BankProductItem(R.string.service_balance, Icons.Rounded.ShoppingCart),
            BankProductItem(R.string.service_extract, Icons.Rounded.Face),
            BankProductItem(R.string.service_social, Icons.Rounded.Build),
            BankProductItem(R.string.service_investment, Icons.Rounded.Call),
            BankProductItem(R.string.service_pix, Icons.Rounded.DateRange)
        )
    }
}