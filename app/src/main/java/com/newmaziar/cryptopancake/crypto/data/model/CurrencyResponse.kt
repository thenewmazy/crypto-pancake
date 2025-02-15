package com.newmaziar.cryptopancake.crypto.data.model

data class CurrencyResponse(
    val amount: Double?,
    val base: String?,
    val date: String?,
    val rates: Map<String, Double>?
)
