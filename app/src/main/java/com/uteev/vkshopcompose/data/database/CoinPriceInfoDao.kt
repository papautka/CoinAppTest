package com.uteev.convertorusdtobtc.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uteev.convertorusdtobtc.domain.pojo.coinprice.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {

    // Чтобы выводить список в порядке убывания времени обновления в RecyclerView
    @Query("SELECT * FROM full_price_list ORDER BY lastUpdate DESC")
    fun getPriceList() : LiveData<List<CoinPriceInfo>>

    @Query("SELECT * FROM full_price_list WHERE fromSymbol = :fSymbol LIMIT 1")
    fun getPriceInfoAboutCoin(fSymbol : String) : LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList : List<CoinPriceInfo>)

}