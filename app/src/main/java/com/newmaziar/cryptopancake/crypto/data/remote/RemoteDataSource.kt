package com.newmaziar.cryptopancake.crypto.data.remote

import com.newmaziar.core_module.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate

internal interface RemoteDataSource {

    suspend fun getExchangeRate(from: String, to: String): ResultWrapper<CurrencyRate>
    suspend fun getCoins(): ResultWrapper<List<CryptoDomain>>
    suspend fun getCoin(symbol: String): ResultWrapper<CryptoDomain>
}