package com.dani.starwars.fakes

import com.dani.data.datasource.local.entities.RecentSearchEntity

object AppModuleTestData {

    val recentSearch1 = RecentSearchEntity("one", 1L)
    val recentSearch2 = RecentSearchEntity("two", 2L)
    val recentSearch3 = RecentSearchEntity("three", 3L)

    val searches = mutableListOf(recentSearch1, recentSearch2, recentSearch3)

}