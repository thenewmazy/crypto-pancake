package com.newmaziar.cryptopancake.crypto.data.remote

import com.newmaziar.core_module.util.ResultWrapper
import com.newmaziar.core_module.util.mapResponse
import com.newmaziar.core_module.util.safeApiCall
import com.newmaziar.cryptopancake.crypto.data.network.Api
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import com.newmaziar.cryptopancake.crypto.domain.model.toDomain
import com.newmaziar.cryptopancake.crypto.extensions.orDefault
import kotlin.random.Random

class RemoteDataSourceImpl(val api: Api) : RemoteDataSource {

    override suspend fun getExchangeRate(from: String, to: String): ResultWrapper<CurrencyRate> =
        safeApiCall {
            api.getLatestCurrencyRates(from, to)
        }.mapResponse { it.toDomain(to) }

    override suspend fun getCoins(): ResultWrapper<List<CryptoDomain>> =
        safeApiCall { api.getCryptoRates() }.mapResponse { response ->
            response.map { it.toDomain() }
        }

    //TODO use: mapResponse{ it.toDomain() }
    // As the API do not return more data I added some Demo data here and I am tiered :)
    override suspend fun getCoin(symbol: String): ResultWrapper<CryptoDomain> =
        safeApiCall { api.getCryptoRateBySymbol(symbol) }.mapResponse {
            CryptoDomain(
                name = it.baseAsset.orEmpty(),
                symbol = it.symbol.orEmpty(),
                priceUSD = it.lastPrice?.toDouble().orDefault(),
                openPrice = it.openPrice?.toDoubleOrNull()?.let { price ->
                    if (price == it.lastPrice?.toDouble().orDefault()) {
                        price + Random.nextDouble(-(price / 5), price / 5)
                    } else {
                        price
                    }
                } ?: -1.0,

                volume = if (it.volume.orEmpty() == "0")
                    Random.nextInt(31424, 24193123)
                else
                    it.volume?.toInt() ?: 3546
            )
        }
}