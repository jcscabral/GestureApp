package com.example.gestureapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.ShoppingCart
import com.example.gestureapp.R
import com.example.gestureapp.model.BankService


class DataSource {
    fun load(): List<BankService>{
        return listOf(
            BankService(R.string.service_balance, Icons.Rounded.ShoppingCart),
            BankService(R.string.service_extract, Icons.Rounded.Face),
            BankService(R.string.service_social, Icons.Rounded.Build),
            BankService(R.string.service_investment, Icons.Rounded.Call),
            BankService(R.string.service_pix, Icons.Rounded.DateRange)
        )
    }
}