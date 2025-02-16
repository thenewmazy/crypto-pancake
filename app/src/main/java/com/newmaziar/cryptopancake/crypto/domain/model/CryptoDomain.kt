package com.newmaziar.cryptopancake.crypto.domain.model

import com.newmaziar.cryptopancake.crypto.data.model.CryptoTickerResponse
import com.newmaziar.cryptopancake.crypto.extensions.orDefault

data class CryptoDomain(
    val name: String,
    val symbol: String,
    var priceUSD: Double,
    val openPrice: Double,
    val volume: Int
)

// TODO Handle NumberFormatException later
internal fun CryptoTickerResponse.toDomain(): CryptoDomain = CryptoDomain(
    name = this.baseAsset.orEmpty(),
    symbol = this.symbol.orEmpty(),
    priceUSD = this.lastPrice?.toDouble().orDefault(),
    openPrice = this.openPrice?.toDouble().orDefault(),
    volume = this.volume?.toInt() ?: 0
)

