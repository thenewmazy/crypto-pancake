package com.newmaziar.cryptopancake.crypto.view.compose.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newmaziar.cryptopancake.crypto.domain.model.CryptoDomain
import com.newmaziar.cryptopancake.crypto.view.model.CryptoUi
import com.newmaziar.cryptopancake.crypto.view.model.getCurrency
import com.newmaziar.cryptopancake.crypto.view.model.getPrice
import com.newmaziar.cryptopancake.crypto.view.model.toUiModel

val previewDummyCrypto = CryptoDomain(
    name = "Bitcoin",
    symbol = "BTC",
    priceUSD = 2334454.0,
    openPrice = 3233494.0,
    volume = 2334454
).toUiModel(2.3)

@Composable
fun CryptoItem(
    modifier: Modifier = Modifier,
    crypto: CryptoUi,
    onClickItem: (CryptoUi) -> Unit,
    isUSUser: Boolean,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickItem(crypto) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = crypto.icon),
                contentDescription = crypto.name,
                modifier = Modifier.size(46.dp)
            )
            Text(
                text = crypto.name,
                fontSize = 25.sp,
            )
        }
        Text(
            fontSize = 16.sp,
            text = "${crypto.getPrice(isUSUser).formated} ${getCurrency(isUSUser)}"
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Composable
fun CryptoItemPreview(modifier: Modifier = Modifier) {
    CryptoItem(
        crypto = previewDummyCrypto,
        isUSUser = true,
        onClickItem = {}
    )
}


