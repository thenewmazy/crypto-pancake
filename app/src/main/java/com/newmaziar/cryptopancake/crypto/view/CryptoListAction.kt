package com.newmaziar.cryptopancake.crypto.view

import com.newmaziar.cryptopancake.crypto.view.model.CryptoUi

sealed class CryptoListAction {
    data class CryptoClicked(val crypto: CryptoUi) : CryptoListAction()
    data object RefreshCryptoData : CryptoListAction()
    data object SwitchCurrency : CryptoListAction()
    data object BackPressed : CryptoListAction()
}