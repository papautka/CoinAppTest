package com.uteev.convertorusdtobtc.domain.pojo.coininfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.uteev.convertorusdtobtc.domain.pojo.coininfo.CoinInfo

data class Datum(
     @SerializedName("CoinInfo")
     @Expose
     val coinInfo: CoinInfo? = null
)
