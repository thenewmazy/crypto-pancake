@file:OptIn(ExperimentalLayoutApi::class)

package com.newmaziar.cryptopancake.crypto.view.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.newmaziar.cryptopancake.R
import com.newmaziar.cryptopancake.crypto.view.compose.items.CryptoCard
import com.newmaziar.cryptopancake.crypto.view.compose.items.previewDummyCrypto
import com.newmaziar.cryptopancake.crypto.view.model.CryptoListState
import com.newmaziar.cryptopancake.crypto.view.model.getPrice
import com.newmaziar.cryptopancake.ui.theme.CryptoPancakeTheme

@Composable
fun CryptoDetailScreen(
    state: CryptoListState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else if (state.selectedItem != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FlowRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                CryptoCard(
                    title = state.selectedItem.name,
                    detail = state.selectedItem.symbol,
                    icon = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier
                )
                CryptoCard(
                    title = stringResource(R.string.price),
                    detail = state.selectedItem.getPrice(state.isUserUS).formated,
                    icon = Icons.Filled.CurrencyExchange,
                    modifier = Modifier
                )
                CryptoCard(
                    title = context.getString(R.string.volume),
                    detail = "${state.selectedItem.volume}",
                    icon = Icons.Filled.Assessment,
                    modifier = Modifier
                )
                CryptoCard(
                    title = context.getString(R.string.change),
                    detail = context.getString(R.string.percent_value, state.selectedItem.trend),
                    icon = volumeIcon(state.selectedItem.trend >= 0),
                    modifier = Modifier
                )
            }
        }
    }
}


private fun volumeIcon(isPositive: Boolean) =
    if (isPositive) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown


@PreviewLightDark
@Composable
fun CryptoDetailScreenPreview(modifier: Modifier = Modifier) {
    CryptoPancakeTheme {
        CryptoDetailScreen(
            state = CryptoListState(
                isLoading = false,
                cryptoList = emptyList(),
                isUserUS = false,
                selectedItem = previewDummyCrypto
            ),
            modifier = modifier
        )
    }
}