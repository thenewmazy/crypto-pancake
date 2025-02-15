package com.newmaziar.cryptopancake.crypto.data

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.data.local.LocalDataStore
import com.newmaziar.cryptopancake.crypto.data.remote.RemoteDataSource
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import kotlinx.coroutines.flow.Flow

internal class CryptoRepositoryImp(
    private val remoteDataStore: RemoteDataSource,
    private val localDataStore: LocalDataStore
) : CryptoRepository {

    // Dummy API call (replace with your actual API call)
    override suspend fun fetchCryptoData(): ResultWrapper<List<CryptoDomain>> =
        remoteDataStore.getCoins()

    override suspend fun getExchangeRate(from: String, to: String): ResultWrapper<CurrencyRate> =
        remoteDataStore.getExchangeRate(from, to)

    override val currency: Flow<String> = localDataStore.currency

    override suspend fun saveCurrency(isUSUser: Boolean) = localDataStore.saveCurrency(isUSUser)

    override suspend fun getSavedCurrency(): String = ""

}


