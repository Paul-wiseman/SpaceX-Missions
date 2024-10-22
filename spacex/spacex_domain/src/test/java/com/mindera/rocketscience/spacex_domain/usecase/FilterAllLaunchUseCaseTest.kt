package com.mindera.rocketscience.spacex_domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import com.mindera.rocketscience.spacex_domain.test_data.TestData
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FilterAllLaunchUseCaseTest {
    private lateinit var filterAllLaunchUseCase: FilterAllLaunchUseCase
    private val repository: SpaceXRepository = mockk()

    @Before
    fun setUp() {
        filterAllLaunchUseCase = FilterAllLaunchUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun shouldReturnFilteredLaunchItemWithYearAndSuccessfulLaunch() = runTest {
        val year = TestData.launchItem.year.toInt()
        val onlySuccessfulLaunch = true
        val expected = TestData.launchItem
        coEvery { repository.getSpaceXLaunchItem() } returns flowOf(PagingData.from(listOf(expected)))

        val actualData = filterAllLaunchUseCase(year, onlySuccessfulLaunch)

        actualData.collect { value: PagingData<SpaceXLaunchItem> ->
            value.map { actual: SpaceXLaunchItem ->
                assertEquals(expected, actual)
            }
        }
    }

    @Test
    fun shouldReturnFilteredLaunchItemWithUnSuccessfulLaunch() = runTest {
        val year = null
        val onlySuccessfulLaunch = false
        val expected = TestData.launchItem.copy(success = onlySuccessfulLaunch)
        coEvery { repository.getSpaceXLaunchItem() } returns flowOf(PagingData.from(listOf(expected)))

        val actualData = filterAllLaunchUseCase(year, onlySuccessfulLaunch)

        actualData.collect { value: PagingData<SpaceXLaunchItem> ->
            value.map { actual: SpaceXLaunchItem ->
                assertEquals(expected, actual)
            }
        }
    }
}