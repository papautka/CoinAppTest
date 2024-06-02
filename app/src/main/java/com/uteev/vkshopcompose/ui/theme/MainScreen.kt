package com.uteev.vkshopcompose.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uteev.convertorusdtobtc.domain.pojo.coinprice.CoinPriceInfo
import com.uteev.convertorusdtobtc.presentation.CoinViewModel

@Composable
fun MainScreen(viewModel: CoinViewModel = viewModel()) {
    val coins by viewModel.priceList.collectAsState(initial = emptyList())
    val selectedCoin = remember { mutableStateOf<CoinPriceInfo?>(null) }

    MaterialTheme {
        if (selectedCoin.value != null) {
            CoinDetails(coin = selectedCoin.value!!)
        } else {
            CoinPriceList(coins = coins) { coin ->
                selectedCoin.value = coin
            }
        }
    }
}

