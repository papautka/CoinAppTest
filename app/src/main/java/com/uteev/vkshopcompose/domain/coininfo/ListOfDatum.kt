package com.uteev.convertorusdtobtc.domain.pojo.coininfo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.uteev.convertorusdtobtc.domain.pojo.coininfo.Datum

data class ListOfDatum(
    @SerializedName("Data")
    @Expose
    val data: List<Datum>? = null
)
