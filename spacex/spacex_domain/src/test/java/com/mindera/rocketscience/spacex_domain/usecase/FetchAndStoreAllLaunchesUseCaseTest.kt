package com.mindera.rocketscience.spacex_domain.usecase

import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FetchAndStoreAllLaunchesUseCaseTest {

    private lateinit var fetchAndStoreAllLaunchesUseCase: FetchAndStoreAllLaunchesUseCase
    private val repository: SpaceXRepository = mockk()

    @Before
    fun setUp() {
        fetchAndStoreAllLaunchesUseCase = FetchAndStoreAllLaunchesUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun shouldFetchAndStoreAllLaunchesFromRepository() = runTest {
        val expected = Result.success(true)

        coEvery { repository.fetchAndStoreALlSpaceItemLaunches() } returns expected

        val actual = fetchAndStoreAllLaunchesUseCase()

        assertEquals(expected, actual)
    }

    @Test
    fun shouldReturnErrorResultWhenFetchAndStoreAllLaunchesThrowAnFromRepositoryException() = runTest {
        val expected = Result.success(false)

        coEvery { repository.fetchAndStoreALlSpaceItemLaunches() } returns expected

        val actual = fetchAndStoreAllLaunchesUseCase()

        assertEquals(expected, actual)
    }
}