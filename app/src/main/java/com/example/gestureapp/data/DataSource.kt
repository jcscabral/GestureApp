package com.example.gestureapp.data
import java.math.BigInteger
import com.example.gestureapp.R
import com.example.gestureapp.model.BankProductItem

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
                BankProductItem(R.string.service_balance, R.drawable.saldo),
                BankProductItem(R.string.service_extract, R.drawable.extrato),
                BankProductItem(R.string.service_cards, R.drawable.cartoes ),
                BankProductItem(R.string.service_investment, R.drawable.investimentos),
                BankProductItem(R.string.service_international, R.drawable.internacional),
                BankProductItem(R.string.service_payment, R.drawable.pagar),
                BankProductItem(R.string.service_pix, R.drawable.pix)
            )

        val pixSendServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_schedule, R.drawable.agendar),
                BankProductItem(R.string.pix_copy_paste, R.drawable.copia_cola),
                BankProductItem(R.string.pix_qr_code, R.drawable.qrcode),
                BankProductItem(R.string.pix_charge, R.drawable.cobrar),
                BankProductItem(R.string.pix_transfer, R.drawable.transferir),
                BankProductItem(R.string.pix_credit_card, R.drawable.cartoes),
            )

        val pixReceiveServices: List<BankProductItem> =
            listOf(
                BankProductItem(R.string.pix_charge, R.drawable.cobrar),
                BankProductItem(R.string.pix_deposit, R.drawable.depositar)
            )

        val moneyList: List<Double> =
            listOf(
                820.00,
                1590.00,
//                3499.99,
//                120.00,
//                1330.50,
//                170.80,
//                478.00
            )
        val cpfList: List<String> =
            listOf(
                "01009537431",
                "09199934434", //TODO
//                "06851150387",
//                "00846557479",
//                "12345678901",
//                "12345678901",
//                "12345678901"
            )

        val namesList: List<String> =
            listOf(
                "Júlio César de Sousa Cabral",
                "Maria Antônia Firmino",
//                "Paulo Gonçalves de Almeida",
//                "Joâo Antônio Ferreira da Silva",
//                "Helena dos Anjos Mello",
//                "Fábio Madureira Cavalcante",
//                "Ana Carolina Rossi"
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

