package com.newmaziar.cryptopancake.crypto.domain.usecase

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate

class GetCurrencyRateUseCase(private val repository: CryptoRepository) {
    suspend operator fun invoke(from: String, to: String): ResultWrapper<CurrencyRate> {
        return repository.getExchangeRate(from, to)
    }
}