package com.mindera.rocketscience.spacex_domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import com.mindera.rocketscience.spacex_domain.test_data.TestData.launchItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class GetAllLaunchUseCaseTest {

    private lateinit var getAllLaunchUseCase: GetAllLaunchUseCase
    private val repository: SpaceXRepository = mockk()

    @Before
    fun setUp() {
        getAllLaunchUseCase = GetAllLaunchUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun shouldReturnLaunchFlowFromRepository() = runTest {

        val expectedLaunchItems = PagingData.from(listOf(launchItem))
        val expectedFlow = flowOf(expectedLaunchItems)

        every { repository.getSpaceXLaunchItem() } returns expectedFlow

        getAllLaunchUseCase().test {
            assertEquals(expectedLaunchItems, awaitItem())
            awaitComplete()
        }
    }
}