package com.newmaziar.cryptopancake.crypto.view.compose.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newmaziar.cryptopancake.ui.theme.CryptoPancakeTheme

@Composable
fun CryptoCard(
    title: String,
    detail: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .shadow(
                elevation = 10.dp,
                spotColor = MaterialTheme.colorScheme.secondary
            ),
        shape = MaterialTheme.shapes.medium,

        ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = title,
                fontSize = 48.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = detail,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

        }
    }
}

@PreviewLightDark
@Composable
fun CryptoCardPreview(modifier: Modifier = Modifier) {
    CryptoPancakeTheme {
        CryptoCard(
            title = "Bitcoin",
            detail = "BTC",
            icon = ImageVector.vectorResource(id = com.newmaziar.cryptopancake.R.drawable.ic_launcher_foreground),
            modifier = modifier

        )
    }
}