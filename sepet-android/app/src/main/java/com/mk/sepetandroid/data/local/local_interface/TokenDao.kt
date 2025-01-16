package com.mk.sepetandroid.data.local.local_interface

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mk.sepetandroid.data.local.entity.TokenEntity

@Dao
interface TokenDao {
    @Insert
    suspend fun addToken(tokenEntity: TokenEntity)

    @Delete
    suspend fun deleteToken(tokenEntity: TokenEntity)

    @Update
    suspend fun updateToken(tokenEntity: TokenEntity)

    @Query("SELECT * FROM TokenEntity")
    suspend fun getTokens() : List<TokenEntity>

    @Query("DELETE FROM TokenEntity")
    suspend fun deleteAll()
}