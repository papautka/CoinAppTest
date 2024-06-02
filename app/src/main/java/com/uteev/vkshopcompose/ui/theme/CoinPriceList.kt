package com.uteev.vkshopcompose.ui.theme

import CoinCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.uteev.convertorusdtobtc.domain.pojo.coinprice.CoinPriceInfo

@Composable
fun CoinPriceList(coins: List<CoinPriceInfo>, onItemClick: (CoinPriceInfo) -> Unit) {
    LazyColumn {
        items(coins) { coin ->
            CoinCard(coinPriceInfo = coin, onItemClick = onItemClick)
        }
    }
}
