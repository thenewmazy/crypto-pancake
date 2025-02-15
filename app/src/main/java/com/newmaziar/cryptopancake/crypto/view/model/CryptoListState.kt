package com.newmaziar.cryptopancake.crypto.view.model

import androidx.compose.runtime.Immutable
import com.newmaziar.cryptopancake.core.util.Error
import com.newmaziar.cryptopancake.core.util.ErrorResult

@Immutable
data class CryptoListState(
    val isLoading: Boolean = false,
    val cryptoList: List<CryptoUi> = emptyList(),
    val error: Error = ErrorResult.Reset,
    val selectedItem: CryptoUi? = null,
    val isUserUS: Boolean = false,
    val exchangeRate: Double = 0.0
)