package com.uteev.convertorusdtobtc.domain.pojo.coininfo

import androidx.room.Entity
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfo (
    @SerializedName("Name")
    @Expose
    val name: String
)
