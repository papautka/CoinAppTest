package com.uteev.convertorusdtobtc.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.uteev.convertorusdtobtc.data.database.AppDatabase
import com.uteev.convertorusdtobtc.domain.pojo.coinprice.CoinPriceInfo
import com.uteev.convertorusdtobtc.domain.pojo.coinprice.CoinPriceInfoRawData
import com.uteev.vkshopcompose.data.api.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoinViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    private val _priceList = MutableStateFlow<List<CoinPriceInfo>>(emptyList())
    val priceList: StateFlow<List<CoinPriceInfo>> get() = _priceList

    private val _topCoinsInfo = MutableStateFlow<List<String>?>(null)
    val topCoinsInfo: StateFlow<List<String>?> get() = _topCoinsInfo

    private val _fullPriceList = MutableStateFlow<Map<String, Map<String, CoinPriceInfo>>?>(null)
    val fullPriceList: StateFlow<Map<String, Map<String, CoinPriceInfo>>?> get() = _fullPriceList

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    val topCoins = ApiFactory.apiService.getTopCoinsInfo().blockingGet()
                    val topCoinNames = topCoins.data?.mapNotNull { it.coinInfo?.name }.orEmpty()
                    _topCoinsInfo.value = topCoinNames

                    val fullPriceResponse = ApiFactory.apiService.getFullPriceList(fromSymbol = topCoinNames.joinToString(",")).blockingGet()
                    val priceList = getPriceListFromRawData(fullPriceResponse)
                    _priceList.value = priceList

                    withContext(Dispatchers.IO) {
                        db.coinPriceInfoDao().insertPriceList(priceList)
                    }
                } catch (e: Exception) {
                    Log.d("LOAD_DATA", e.toString())
                    e.printStackTrace()
                }
                delay(10_000) // Wait for 10 seconds before repeating the request
            }
        }
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = mutableListOf<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        val coinKeySet = jsonObject.keySet()

        for (key in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(key)
            val currencyKeySet = currencyJson.keySet()

            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinPriceInfo::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
    }
}
