package com.newmaziar.cryptopancake.crypto.data.remote

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.core.util.mapResponse
import com.newmaziar.cryptopancake.core.util.safeApiCall
import com.newmaziar.cryptopancake.crypto.data.network.Api
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import com.newmaziar.cryptopancake.crypto.extensions.orDefault

class RemoteDataSourceImpl(val api: Api) : RemoteDataSource {


    override suspend fun getExchangeRate(from: String, to: String): ResultWrapper<CurrencyRate> =
        safeApiCall {
            api.getLatestCurrencyRates(from, to)
        }.mapResponse {
            CurrencyRate(
                rate = it.rates?.get(to).orDefault(),
                currency = to
            )
        }

    override suspend fun getCoins(): ResultWrapper<List<CryptoDomain>> =
        safeApiCall { api.getCryptoRates() }.mapResponse {
            it.map {
                CryptoDomain(
                    name = it.baseAsset.orEmpty(),
                    priceUSD = it.lastPrice?.toDouble().orDefault()
                )
            }
        }
}
