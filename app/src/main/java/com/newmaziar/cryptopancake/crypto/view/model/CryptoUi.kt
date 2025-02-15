package com.newmaziar.cryptopancake.crypto.view.model

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.newmaziar.cryptopancake.R
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import java.util.Locale

data class CryptoUi(
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground,
    val name: String,
    var priceUSD: FormatedNumber,
    var priceSEK: FormatedNumber
)

data class FormatedNumber(val value: Double, val formated: String)

fun CryptoDomain.toUiModel(rate: Double): CryptoUi {
    return CryptoUi(
        name = name.uppercase(Locale.ROOT),
        priceUSD = priceUSD.toFormatedNumber(),
        priceSEK = (priceUSD * rate).toFormatedNumber()
    )
}

private fun Double.toFormatedNumber(): FormatedNumber {
    // Keep it here as assigning Locale.getDefault() to a final static field is suspicious;
    // this code will not work correctly if the user changes locale while the app is running.
    val formater = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 2
        minimumFractionDigits = 2
    }
    return FormatedNumber(
        value = this,
        formated = formater.format(this)
    )
}

