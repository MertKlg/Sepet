package com.mk.sepetandroid.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mk.sepetandroid.data.local.entity.TokenEntity
import com.mk.sepetandroid.data.local.local_interface.TokenDao

@Database(
    entities = [
        TokenEntity::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class SepetDatabase : RoomDatabase() {
    abstract fun tokenDao(): TokenDao
}