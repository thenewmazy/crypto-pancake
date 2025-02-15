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


    @GET("https://api.wazirx.com/sapi/v1/tickers/24hr")
    suspend fun getCryptoRates(): Response<List<CryptoTickerResponse>>
}
