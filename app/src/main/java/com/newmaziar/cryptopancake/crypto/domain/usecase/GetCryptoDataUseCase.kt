package com.newmaziar.cryptopancake.crypto.domain.usecase

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain

class GetCryptoDataUseCase(private val repository: CryptoRepository) {
    suspend operator fun invoke(): ResultWrapper<List<CryptoDomain>> {
        return repository.fetchCryptoData()
    }
}
