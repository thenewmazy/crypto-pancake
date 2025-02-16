package com.newmaziar.cryptopancake.crypto.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newmaziar.core_module.util.ErrorResult
import com.newmaziar.core_module.util.ResultWrapper
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.domain.model.CurrencyRate
import com.newmaziar.cryptopancake.crypto.view.model.CryptoListState
import com.newmaziar.cryptopancake.crypto.view.model.CryptoUi
import com.newmaziar.cryptopancake.crypto.view.model.toUiModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CryptoViewModel(
    private val repository: CryptoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CryptoListState())
    val state = _state
        .onStart { loadInitialData() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = CryptoListState()
        )

    fun action(action: CryptoListAction) {
        when (action) {
            is CryptoListAction.CryptoClicked -> onCryptoClicked(action.crypto)
            is CryptoListAction.RefreshCryptoListData -> fetchCryptoData()
            is CryptoListAction.SwitchCurrency -> switchCurrency()
            is CryptoListAction.BackPressed -> {} //TODO
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val results = listOf(
                async { repository.getExchangeRate(from = "USD", to = "SEK") },
                async { repository.fetchCryptoList() }
            ).awaitAll()
            val currencyRateResult = results[0] as ResultWrapper<*>
            val cryptoDataResult = results[1] as ResultWrapper<*>
            val newState = when {
                currencyRateResult is ResultWrapper.Fail -> _state.value.copy(
                    isLoading = false,
                    error = currencyRateResult.reason
                )

                cryptoDataResult is ResultWrapper.Fail -> _state.value.copy(
                    isLoading = false,
                    error = cryptoDataResult.reason
                )

                currencyRateResult is ResultWrapper.Success &&
                        cryptoDataResult is ResultWrapper.Success -> {

                    val currencyRate = (currencyRateResult.value as CurrencyRate).rate
                    _state.value.copy(
                        isLoading = false,
                        exchangeRate = currencyRate,
                        cryptoList = (cryptoDataResult.value as List<CryptoDomain>).map {
                            it.toUiModel(currencyRate)
                        },
                        error = ErrorResult.Reset
                    )
                }

                else -> _state.value.copy(isLoading = false, error = ErrorResult.GenericError())

            }
            _state.update { newState }
        }
    }

    private fun fetchCryptoData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, cryptoList = emptyList()) }
            when (val result = repository.fetchCryptoList()) {
                is ResultWrapper.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            cryptoList = result.value.map { it.toUiModel(state.value.exchangeRate) },
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

    private fun fetchCryptoDetail(symbol: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = repository.fetchCrypto(symbol)) {
                is ResultWrapper.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            selectedItem = result.value.toUiModel(state.value.exchangeRate),
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

    private fun onCryptoClicked(crypto: CryptoUi) {
        _state.update { it.copy(selectedItem = crypto) }
        fetchCryptoDetail(crypto.symbol)
    }
}


