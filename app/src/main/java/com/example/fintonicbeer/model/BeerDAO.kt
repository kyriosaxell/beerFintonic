package com.example.fintonicbeer.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BeerDAO {
    @Insert
    suspend fun insertAll(vararg beer: Beer): List<Long>

    @Query("SELECT * FROM beer")
    suspend fun getAllBeers(): List<Beer>

    @Query("SELECT * FROM beer WHERE uuid = :beerId")
    suspend fun getBeerById(beerId: Int): Beer

    @Query("DELETE FROM Beer")
    suspend fun deleteAllBeers()
}