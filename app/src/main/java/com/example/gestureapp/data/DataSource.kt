package com.example.gestureapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.ShoppingCart
import com.example.gestureapp.R
import com.example.gestureapp.model.BankProductItem
import java.math.BigInteger

const val CPF_SIZE = 11
const val PASSWORD = "54837190"
const val MAX_PASSWORD_SIZE  = PASSWORD.length

const val KEY_OK = "OK"
const val KEY_BACKSPACE = "\u232b"

const val TEN = "10"
val FACTOR_TEN = BigInteger(TEN)

abstract class DataSource {
    companion object {

        val bankServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.service_balance, Icons.Rounded.ShoppingCart),
                BankProductItem(R.string.service_extract, Icons.Rounded.Face),
                BankProductItem(R.string.service_social, Icons.Rounded.Build),
                BankProductItem(R.string.service_investment, Icons.Rounded.Call),
                BankProductItem(R.string.service_international, Icons.Rounded.MailOutline),
                BankProductItem(R.string.service_payment, Icons.Rounded.DateRange),
                BankProductItem(R.string.service_pix, Icons.Rounded.DateRange)
            )

        val pixSendServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_schedule, Icons.Rounded.Face),
                BankProductItem(R.string.pix_copy_paste, Icons.Rounded.Build),
                BankProductItem(R.string.pix_qr_code, Icons.Rounded.Call),
                BankProductItem(R.string.pix_transfer, Icons.Rounded.ShoppingCart),
                BankProductItem(R.string.pix_charge, Icons.Rounded.MailOutline),
                BankProductItem(R.string.pix_credit_card, Icons.Rounded.ShoppingCart),
            )

        val pixReceiveServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_charge, Icons.Rounded.ShoppingCart),
                BankProductItem(R.string.pix_deposit, Icons.Rounded.Face)
            )

        val moneyList: List<Double> =
            listOf(
                820.00,
                1590.00,
                3499.99,
                120.00,
                1330.50,
                170.80,
                478.00
            )
        val cpfList: List<String> =
            listOf(
                "01009537431",
                "12345678901", //TODO
                "12345678901",
                "12345678901",
                "12345678901",
                "12345678901",
                "12345678901"
            )

        val gender =  listOf(
            "Masculino",
            "Feminino"
        )
        val useOption =  listOf(
            "Teste",
            "Avaliação"
        )

        val keyboardDigits = listOf(
            "1", "2", "3", KEY_BACKSPACE, "4", "5", "6", KEY_OK, "7", "8", "9", "", "", "0", "", ""
        )


    }

}

