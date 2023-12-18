package com.dani.data.datasource.repository.vehicle

import com.dani.data.datasource.repository.CoroutineTest
import com.dani.data.fakes.FakeRemoteVehicleDataSource
import com.dani.data.fakes.Shared
import com.dani.shared_test.fakes.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class VehicleRepositoryImplTest : CoroutineTest(){

    @Test
    fun getVehicle_success() = runBlockingTest {
        // Given a repository with a datasource that returns values successfully
        val vehicleDataSource = FakeRemoteVehicleDataSource
        val repository: VehicleRepository = VehicleRepositoryImpl(vehicleDataSource, testDispatcher)

        // When get vehicle is performed
        val result = repository.getVehicle("testId")

        // Assert values
        assertEquals(result, TestData.vehicle)
    }

    @Test
    fun getVehicle_failure() = runBlockingTest {
        // Given a repository with a datasource that returns values successfully
        val vehicleDataSource = FakeRemoteVehicleDataSource
        val repository: VehicleRepository = VehicleRepositoryImpl(vehicleDataSource, testDispatcher)

        // When get vehicle is performed
        val result = repository.getVehicle("testId")

        // Assert values
        assertEquals(result, Shared.resultException)
    }
}