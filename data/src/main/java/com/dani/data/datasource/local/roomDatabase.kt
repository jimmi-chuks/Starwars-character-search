package com.dani.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dani.data.datasource.local.daos.RecentSearchDao
import com.dani.data.datasource.local.entities.RecentSearchEntity

@Database(
    entities = [RecentSearchEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun recentSearchDao(): RecentSearchDao
}