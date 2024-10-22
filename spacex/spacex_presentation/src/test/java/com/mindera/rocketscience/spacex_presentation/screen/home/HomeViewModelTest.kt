package com.mindera.rocketscience.spacex_presentation.screen.home

import androidx.paging.PagingData
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.mindera.rocketscience.preference.SpaceXPreference
import com.mindera.rocketscience.spacex_domain.usecase.SpaceXUseCases
import com.mindera.rocketscience.spacex_presentation.TestDate.getCompanyInfo
import com.mindera.rocketscience.spacex_presentation.TestDate.spaceXItems
import com.mindera.rocketscience.util.UiText
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val sharedPreferences: SpaceXPreference = mockk()
    private val spaceXUseCases: SpaceXUseCases = mockk()
    private lateinit var homeViewModel: HomeViewModel
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        val pagingData = PagingData.from(listOf(spaceXItems))
        every { spaceXUseCases.getAllLaunchUseCase.invoke() } returns flowOf(pagingData)
        every { sharedPreferences.getScrollPosition() } returns 22

        homeViewModel = HomeViewModel(spaceXUseCases, sharedPreferences)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun getCompanyInfoShouldUpdateUiStateWithCompanyInfoOnSuccess() = runTest {
        val expected = getCompanyInfo
        every { spaceXUseCases.getCompanyInfoUseCase.invoke() } returns flowOf(
            Result.success(
                getCompanyInfo
            )
        )

        homeViewModel.getCompanyInfo()
        val actual = mutableListOf<HomeScreenUiState>()
        homeViewModel.uiState.take(2).collect {
            actual.add(it)
        }


        assertThat(expected.name).isEqualTo(actual[1].companyName)
        assertThat(expected.founder).isEqualTo(actual[1].founderName)
        assertThat(expected.employees).isEqualTo(actual[1].employees)
    }

    @Test
    fun getCompanyInfoShouldReturnDefaultValuesWhenGetCompanyInfoFails() = runTest {

        every { spaceXUseCases.getCompanyInfoUseCase.invoke() } returns flowOf(
            Result.failure(
                Exception(ERROR)
            )
        )

        homeViewModel.getCompanyInfo()
        val actual = mutableListOf<HomeScreenUiState>()
        homeViewModel.uiState.take(2).collect {
            actual.add(it)
        }

        assertThat(actual[1].error).isInstanceOf(UiText.DynamicString::class.java)
    }


    @Test
    fun fetchAllSpaceXLaunchItemsShouldUpdateUiStateWithNoErrorsWhenUseCaseReturnsSuccess() =
        runTest {

            coEvery { spaceXUseCases.fetchAndStoreAllLaunchesUseCase.invoke() } returns Result.success(
                true
            )

            homeViewModel.fetchAllSpaceXLaunchItems()


            homeViewModel.uiState.test {
                assertThat(awaitItem().error).isEqualTo(null)
            }
        }

    @Test
    fun fetchAllSpaceXLaunchItemsShouldUpdateUiStateWithErrorWhenUseCaseReturnsFailure() = runTest {

        coEvery { spaceXUseCases.fetchAndStoreAllLaunchesUseCase.invoke() } returns Result.failure(
            Exception(ERROR)
        )

        homeViewModel.fetchAllSpaceXLaunchItems()

        val actual = mutableListOf<HomeScreenUiState>()
        homeViewModel.uiState.take(2).collect {
            actual.add(it)
        }

        assertThat(actual[1].error).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun shouldStoreScrollPosition() = runTest {

        scope.launch {
            every { sharedPreferences.persistScrollPosition(22) } just Runs

            homeViewModel.persistScrollPosition(22)


            coVerify { sharedPreferences.persistScrollPosition(22) }
        }

    }

    @Test
    fun shouldFilterAllLaunchesWhenEventIsFilterSpaceXItems() = runTest {

        every { spaceXUseCases.filterAllLaunchUseCase(any(), any()) } returns mockk()

        val event = HomeScreenEvent.FilterSpaceXItems(
            year = 2023, onlySuccessfulLaunch = false

        )

        homeViewModel.onEvent(event)


        coVerify { spaceXUseCases.filterAllLaunchUseCase(year = 2023, false) }

    }

    private companion object {
        const val ERROR = "something went wrong"
    }


}