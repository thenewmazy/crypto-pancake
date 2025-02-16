package com.newmaziar.cryptopancake.crypto.domain

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    suspend fun fetchCryptoList(): ResultWrapper<List<CryptoDomain>>
    suspend fun fetchCrypto(symbol: String): ResultWrapper<CryptoDomain>
    suspend fun getExchangeRate(from: String, to: String): ResultWrapper<CurrencyRate>
    suspend fun saveCurrency(isUSUser: Boolean)
    suspend fun getSavedCurrency(): String?
    val currency: Flow<String>
}