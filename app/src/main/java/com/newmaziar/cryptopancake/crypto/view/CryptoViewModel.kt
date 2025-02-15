package com.newmaziar.cryptopancake.crypto.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newmaziar.cryptopancake.core.util.ErrorResult
import com.newmaziar.cryptopancake.core.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.usecase.GetCryptoDataUseCase
import com.newmaziar.cryptopancake.crypto.domain.usecase.GetCurrencyRateUseCase
import com.newmaziar.cryptopancake.crypto.view.model.CryptoListState
import com.newmaziar.cryptopancake.crypto.view.model.CryptoUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CryptoViewModel(
    private val getCryptoDataUseCase: GetCryptoDataUseCase,
    private val getCurrencyRateUseCase: GetCurrencyRateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CryptoListState())
    val state = _state
        .onStart { loadCurrencyRate() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = CryptoListState()
        )

    fun action(action: CryptoListAction) {
        when (action) {
            is CryptoListAction.CryptoClicked -> onCryptoClicked(action.crypto)
            is CryptoListAction.RefreshCryptoData -> fetchCryptoData()
            is CryptoListAction.SwitchCurrency -> switchCurrency()
            is CryptoListAction.BackPressed -> {} //TODO
        }
    }

    private fun loadCurrencyRate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getCurrencyRateUseCase(from = "USD", to = "SEK")) {
                is ResultWrapper.Success -> {
                    _state.update { it.copy(exchangeRate = result.value.rate) }
                    fetchCryptoData()
                }
                //TODO deactivate switchCurrency() when error occurs???
                is ResultWrapper.Fail -> {
                    fetchCryptoData()
                    _state.update { it.copy(isLoading = false, error = result.reason) }
                }
            }
        }
    }

    private fun fetchCryptoData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getCryptoDataUseCase(state.value.exchangeRate)) {
                is ResultWrapper.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            cryptoList = result.value,
                            error = ErrorResult.Reset
                        )
                    }
                }

                is ResultWrapper.Fail -> {
                    _state.update { it.copy(isLoading = false, error = result.reason) }
                }
            }

        }
    }

    private fun switchCurrency() = _state.update { it.copy(isUserUS = !it.isUserUS) }
}

private fun onCryptoClicked(crypto: CryptoUi) {}

