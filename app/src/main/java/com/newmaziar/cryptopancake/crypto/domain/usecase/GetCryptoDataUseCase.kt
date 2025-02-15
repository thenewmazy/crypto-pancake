package com.newmaziar.cryptopancake.crypto.domain.usecase

import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.core.util.mapResponse
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.view.model.CryptoUi
import com.newmaziar.cryptopancake.crypto.view.model.toUiModel

class GetCryptoDataUseCase(private val repository: CryptoRepository) {
    suspend operator fun invoke(rate: Double): ResultWrapper<List<CryptoUi>> {
        return when (val result = repository.fetchCryptoData()) {
            is ResultWrapper.Success -> result.mapResponse { list ->
                list.map { it.toUiModel(rate) }
            }

            is ResultWrapper.Fail -> result
        }
    }
}
