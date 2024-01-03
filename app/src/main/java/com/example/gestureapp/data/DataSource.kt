package com.example.gestureapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.ShoppingCart
import com.example.gestureapp.R
import com.example.gestureapp.model.BankProductItem

const val PASSWORD = "54837190"

abstract class DataSource {
    companion object {

        val bankServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.service_balance, Icons.Rounded.ShoppingCart),
                BankProductItem(R.string.service_extract, Icons.Rounded.Face),
                BankProductItem(R.string.service_social, Icons.Rounded.Build),
                BankProductItem(R.string.service_investment, Icons.Rounded.Call),
                BankProductItem(R.string.service_pix, Icons.Rounded.DateRange)
            )

        val pixSendServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_schedule, Icons.Rounded.Face),
                BankProductItem(R.string.pix_copy_paste, Icons.Rounded.Build),
                BankProductItem(R.string.pix_qr_code, Icons.Rounded.Call),
                BankProductItem(R.string.pix_transfer, Icons.Rounded.ShoppingCart),
            )

        val pixReceiveServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_charge, Icons.Rounded.ShoppingCart),
                BankProductItem(R.string.pix_deposit, Icons.Rounded.Face)
            )

        val gender =  listOf(
            "Masculino",
            "Feminino"
        )
        val useOption =  listOf(
            "Teste",
            "Avaliação"
        )

        val keyboardOK = "OK"

        val keyboardDigits = listOf(
            "1", "2", "3", "\u232b", "4", "5", "6", keyboardOK, "7", "8", "9", " ", " ", "0", " ", " "
        )


    }

}

