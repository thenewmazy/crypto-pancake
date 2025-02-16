package com.newmaziar.cryptopancake.crypto.data.network

import com.newmaziar.cryptopancake.crypto.data.model.CryptoTickerResponse
import com.newmaziar.cryptopancake.crypto.data.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("latest")
    suspend fun getLatestCurrencyRates(
        @Query("from") base: String,
        @Query("to") to: String
    ): Response<CurrencyResponse>

    //Not added multiple Retrofit Instances for simplicity of this project
    @GET("https://api.wazirx.com/sapi/v1/tickers/24hr")
    suspend fun getCryptoRates(): Response<List<CryptoTickerResponse>>

    @GET("https://api.wazirx.com/sapi/v1/ticker/24hr")
    suspend fun getCryptoRateBySymbol(
        @Query("symbol") symbol: String
    ): Response<CryptoTickerResponse>
}
