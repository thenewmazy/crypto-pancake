package com.newmaziar.cryptopancake.crypto.domain.model

import com.newmaziar.cryptopancake.crypto.data.model.CurrencyResponse
import com.newmaziar.cryptopancake.crypto.extensions.orDefault

data class CurrencyRate(val currency: String, val rate: Double)

internal fun CurrencyResponse.toDomain(to: String): CurrencyRate = CurrencyRate(
    rate = this.rates?.get(to).orDefault(),
    currency = to
)