package com.dani.data.datasource.repository.specie

import com.dani.data.datasource.remote.specie.RemoteSpecieDataSource
import com.dani.model.dto.Specie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpecieRepositoryImpl @Inject constructor(
    private val specieDataSource: RemoteSpecieDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SpecieRepository {
    override suspend fun getSpecieDetails(id: String): Specie =
        withContext(ioDispatcher) { specieDataSource.getSpecieDetails(id) }
}