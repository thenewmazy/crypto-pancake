package com.newmaziar.cryptopancake.crypto.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.core.content.ContextCompat.getString
import com.newmaziar.cryptopancake.R
import com.newmaziar.cryptopancake.crypto.view.compose.items.CryptoItem
import com.newmaziar.cryptopancake.crypto.view.compose.items.previewDummyCrypto
import com.newmaziar.cryptopancake.crypto.view.model.CryptoListState
import com.newmaziar.cryptopancake.ui.theme.CryptoPancakeTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoListScreen(
    modifier: Modifier = Modifier,
    state: CryptoListState,
    onCurrencySwitch: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Crypto Prices") },
                actions = {
                    IconButton(onClick = onCurrencySwitch) {
                        Text(
                            getString(
                                LocalContext.current,
                                if (state.isUserUS) R.string.currency_usd else R.string.currency_sek
                            )
                        )
                    }
                })
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn {
                    items(state.cryptoList) { crypto ->
                        CryptoItem(crypto = crypto, isUSUser = state.isUserUS)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CryptoListScreenPreview() {
    CryptoPancakeTheme {
        CryptoListScreen(
            state = CryptoListState(
                isLoading = false,
                cryptoList = (1..10).map {
                    previewDummyCrypto
                },
                isUserUS = false,
            ),
            onCurrencySwitch = {},
            modifier = Modifier
        )
    }
}