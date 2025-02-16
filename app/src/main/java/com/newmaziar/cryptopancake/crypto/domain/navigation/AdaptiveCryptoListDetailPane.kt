@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.newmaziar.cryptopancake.crypto.domain.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.newmaziar.cryptopancake.crypto.view.CryptoListAction
import com.newmaziar.cryptopancake.crypto.view.CryptoViewModel
import com.newmaziar.cryptopancake.crypto.view.compose.CryptoDetailScreen
import com.newmaziar.cryptopancake.crypto.view.compose.CryptoListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AdaptiveCryptoListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CryptoViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        navigator = navigator,
        listPane = {
            CryptoListScreen(
                state = state,
                onCurrencySwitch = { viewModel.action(CryptoListAction.SwitchCurrency) },
                onRefresh = { viewModel.action(CryptoListAction.RefreshCryptoListData) },
                onSelectedCoin = {
                    navigator.navigateTo(
                        pane = ListDetailPaneScaffoldRole.Detail
                    )
                    viewModel.action(CryptoListAction.CryptoClicked(it))
                }
            )
        },
        detailPane = {
            CryptoDetailScreen(state = state)
        },
        modifier = modifier
    )
}