package com.dani.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_searches")
data class RecentSearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "search_string")
    var searchString: String,
    var time: Long
)
