package com.mk.sepetandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TokenEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val accessToken: String,
    val refreshToken : String
)
