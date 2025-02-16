package com.newmaziar.cryptopancake.crypto.view.model

import android.icu.text.NumberFormat
import androidx.annotation.DrawableRes
import com.newmaziar.cryptopancake.R
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import java.util.Locale

internal const val USD = "USD"
internal const val SEK = "SEK"
data class CryptoUi(
    @DrawableRes val icon: Int = R.drawable.ic_launcher_foreground,
    val name: String,
    val symbol: String,
    val priceUSD: FormatedNumber,
    val priceSEK: FormatedNumber,
    val trend: Double,
    val volume: Int
)

internal fun CryptoUi.getPrice(isUserUS: Boolean) = if (isUserUS) priceUSD else priceSEK

internal fun getCurrency(isUserUS: Boolean) = if (isUserUS) USD else SEK

data class FormatedNumber(val value: Double, val formated: String)

fun CryptoDomain.toUiModel(rate: Double): CryptoUi {
    return CryptoUi(
        name = name.uppercase(Locale.ROOT),
        symbol = symbol,
        priceUSD = priceUSD.toFormatedNumber(),
        priceSEK = (priceUSD * rate).toFormatedNumber(),
        trend = calculateTrendPercentage(startPrice = openPrice, endPrice = priceUSD),
        volume = volume
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

/**
 * Calculates the trend percentage between a start price and an end price.
 *
 * @return The trend percentage, or 0.0 if the start price is zero
 */
private fun calculateTrendPercentage(startPrice: Double, endPrice: Double): Double {
    if (startPrice == 0.0) return 0.0

    return ((endPrice - startPrice) / startPrice) * 100
}