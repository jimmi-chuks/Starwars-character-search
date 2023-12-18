package com.dani.data.datasource.repository.starship

import com.dani.data.datasource.remote.starship.RemoteStarshipDataSource
import com.dani.model.dto.Starship
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StarshipRepositoryImpl @Inject constructor(
    private val remoteStarshipDataSource: RemoteStarshipDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StarshipRepository {

    override suspend fun getStarship(id: String): Starship =
        withContext(ioDispatcher) { remoteStarshipDataSource.getStarship(id) }
}
