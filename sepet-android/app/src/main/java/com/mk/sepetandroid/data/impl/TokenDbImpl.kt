package com.mk.sepetandroid.data.impl

import com.mk.sepetandroid.data.local.local_interface.TokenDao
import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import javax.inject.Inject

class TokenDbImpl @Inject constructor(
    private val db: TokenDao
) : ITokenDbRepo{

    override suspend fun addToken(token: TokenEntity) {
        db.addToken(token)
    }

    override suspend fun deleteToken(token: TokenEntity) {
        db.deleteToken(token)
    }

    override suspend fun updateToken(token: TokenEntity) {
        db.updateToken(token)
    }

    override suspend fun getTokens(): List<TokenEntity> {
        return db.getTokens()
    }

    override suspend fun deleteAll() {
        db.deleteAll()
    }


}