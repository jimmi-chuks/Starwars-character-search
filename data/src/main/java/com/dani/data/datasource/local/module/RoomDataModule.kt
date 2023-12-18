package com.dani.data.datasource.local.module

import android.content.Context
import androidx.room.Room
import com.dani.data.datasource.local.AppDatabase
import com.dani.data.datasource.local.daos.RecentSearchDao
import dagger.Module
import dagger.Provides

@Module
class RoomDataModule {
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "starwars.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): RecentSearchDao = appDatabase.recentSearchDao()
}