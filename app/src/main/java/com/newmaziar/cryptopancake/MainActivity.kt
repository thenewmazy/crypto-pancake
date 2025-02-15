package com.newmaziar.cryptopancake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newmaziar.cryptopancake.crypto.view.CryptoListAction
import com.newmaziar.cryptopancake.crypto.view.CryptoViewModel
import com.newmaziar.cryptopancake.crypto.view.compose.CryptoListScreen
import com.newmaziar.cryptopancake.ui.theme.CryptoPancakeTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoPancakeTheme {
                val viewModel = koinViewModel<CryptoViewModel>()

                val state by viewModel.state.collectAsStateWithLifecycle()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CryptoListScreen(
                        state = state,
                        modifier = Modifier.padding(innerPadding),
                        onCurrencySwitch = { viewModel.action(CryptoListAction.SwitchCurrency) }
                    )
                }
            }
        }
    }
}