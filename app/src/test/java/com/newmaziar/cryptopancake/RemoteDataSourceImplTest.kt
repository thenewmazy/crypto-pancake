package com.newmaziar.cryptopancake

import com.newmaziar.core_module.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.data.model.CryptoTickerResponse
import com.newmaziar.cryptopancake.crypto.data.model.CurrencyResponse
import com.newmaziar.cryptopancake.crypto.data.network.Api
import com.newmaziar.cryptopancake.crypto.data.remote.RemoteDataSourceImpl
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RemoteDataSourceImplTest {

    @Mock
    private lateinit var api: Api
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSourceImpl(api)
    }

    @Test
    fun `getExchangeRate returns success`() = runTest {
        val from = "USD"
        val to = "SEK"
        val expectedRate = 0.85
        val response = CurrencyResponse(
            amount = 167888640.0,
            base = from,
            date = "2023-03-15",
            rates = mapOf(to to expectedRate)
        )
        `when`(api.getLatestCurrencyRates(from, to)).thenReturn(Response.success(response))

        val result = remoteDataSource.getExchangeRate(from, to)
        assertEquals(ResultWrapper.Success(CurrencyRate(to, expectedRate)), result)
    }

    @Test
    fun `getExchangeRate returns error`() = runTest {
        val from = "USD"
        val to = "SEK"
        `when`(api.getLatestCurrencyRates(from, to)).thenReturn(
            Response.error(
                404,
                "error message".toResponseBody(null)
            )
        )

        val result = remoteDataSource.getExchangeRate(from, to)
        assert(result is ResultWrapper.Fail)
    }

    @Test
    fun `getCoins returns success`() = runTest {
        val expectedCoins = listOf(
            CryptoTickerResponse(
                baseAsset = "Bitcoin",
                symbol = "BTC",
                lastPrice = "40000",
                openPrice = "39000",
                volume = "10000",
                lowPrice = "56666",
                highPrice = "44444"
            ),
            CryptoTickerResponse(
                baseAsset = "Ethereum",
                symbol = "ETH",
                lastPrice = "3000",
                openPrice = "2900",
                volume = "5000",
                lowPrice = "2800",
                highPrice = "3200"
            )
        )
        `when`(api.getCryptoRates()).thenReturn(Response.success(expectedCoins))

        val result = remoteDataSource.getCoins()

        assert(result is ResultWrapper.Success)
        val coins = (result as ResultWrapper.Success).value
        assertEquals(expectedCoins.size, coins.size)
        assertEquals("Bitcoin", coins[0].name)
        assertEquals("BTC", coins[0].symbol)
        assertEquals(40000.0, coins[0].priceUSD, 0.0)
        assertEquals(39000.0, coins[0].openPrice, 0.0)
        assertEquals(10000, coins[0].volume)
    }

    @Test
    fun `getCoins returns error`() = runTest {
        `when`(api.getCryptoRates()).thenReturn(
            Response.error(
                404,
                "error message".toResponseBody(null)
            )
        )

        val result = remoteDataSource.getCoins()
        assert(result is ResultWrapper.Fail)
    }

    @Test
    fun `getCoin returns success`() = runTest {
        val symbol = "BTC"
        val expectedCoin = CryptoTickerResponse(
            baseAsset = "Bitcoin",
            symbol = "BTC",
            lastPrice = "40000",
            openPrice = "39000",
            volume = "10000",
            lowPrice = "56666",
            highPrice = "44444"
        )
        `when`(api.getCryptoRateBySymbol(symbol)).thenReturn(Response.success(expectedCoin))

        val result = remoteDataSource.getCoin(symbol)

        assert(result is ResultWrapper.Success)
        val coin = (result as ResultWrapper.Success).value
        assertEquals("Bitcoin", coin.name)
        assertEquals("BTC", coin.symbol)
        assertEquals(40000.0, coin.priceUSD, 0.0)
        assertEquals(39000.0, coin.openPrice, 0.0)
        assertEquals(10000, coin.volume)
    }

    @Test
    fun `getCoin returns error`() = runTest {
        val symbol = "BTC"
        `when`(api.getCryptoRateBySymbol(symbol = symbol)).thenReturn(
            Response.error(
                404,
                "error message".toResponseBody(null)
            )
        )

        val result = remoteDataSource.getCoin(symbol = symbol)
        assert(result is ResultWrapper.Fail)
    }

    @Test
    fun `getCoin returns success with default values`() = runTest {
        val symbol = "BTC"
        val expectedCoin = CryptoTickerResponse(
            baseAsset = null,
            symbol = null,
            lastPrice = null,
            openPrice = null,
            volume = "0",
            lowPrice = null,
            highPrice = null
        )
        `when`(api.getCryptoRateBySymbol(symbol = symbol)).thenReturn(Response.success(expectedCoin))

        val result = remoteDataSource.getCoin(symbol = symbol)

        assert(result is ResultWrapper.Success)
        val coin = (result as ResultWrapper.Success).value
        assertEquals("", coin.name)
        assertEquals("", coin.symbol)
        assertEquals(-1.0, coin.priceUSD, 0.0)
        assert(coin.openPrice != 0.0)
        assert(coin.volume in 31424..24193123)
    }
}