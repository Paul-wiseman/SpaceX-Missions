package com.mindera.rocketscience.spacex_data.repository

import androidx.paging.PagingSource
import androidx.paging.map
import com.mindera.rocketscience.spacex_data.TestData
import com.mindera.rocketscience.spacex_data.TestData.companyInfoResponse
import com.mindera.rocketscience.spacex_data.local.database.SpaceXDao
import com.mindera.rocketscience.spacex_data.local.entity.SpaceXLaunchItemEntity
import com.mindera.rocketscience.spacex_data.mapper.toSpaceXLaunchItemEntity
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceKtor
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceRetrofit
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import retrofit2.Response
import java.io.IOException


class SpaceXXRepositoryImplTest {

    private lateinit var repository: SpaceXRepositoryImpl
    private val spaceXDao = mockk<SpaceXDao>()
    private val spaceXApiServiceKtor: SpaceXApiServiceRetrofit = mockk()


    @Before
    fun setUp() {
        repository = SpaceXRepositoryImpl(spaceXDao, spaceXApiServiceKtor)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


    @Test
    fun shouldGetCompanyInfoFromApiService() = runTest {
        val expected = companyInfoResponse

        coEvery { spaceXApiServiceKtor.getCompanyInfo() } returns Response.success(expected)

        repository.getCompanyInfo().collect { result ->
            result.onSuccess { actual ->
                assertEquals(expected.name, actual.name)
                assertEquals(expected.founder, actual.founder)
                assertEquals(expected.employees, actual.employees)
                assertTrue(result.isSuccess)
            }
        }
    }

    @Test
    fun shouldReturnFailureWhenGetCompanyInfoFailsWith400() = runTest {
        val expected = "Get company info failed: 400"

        coEvery { spaceXApiServiceKtor.getCompanyInfo() } returns Response.error(
            400,
            "something went wrong".toResponseBody("application/json".toMediaType())
        )

        repository.getCompanyInfo().collect { result ->
            result
                .onFailure {
                    assertTrue(result.isFailure)
                    assertEquals(expected, it.message)
                }
        }
    }

    @Test
    fun shouldReturnFailureWhenGetCompanyInfoThrowsIOException() = runTest {
        coEvery { spaceXApiServiceKtor.getCompanyInfo() }.throws(IOException("400"))

        repository.getCompanyInfo().collect { result ->
            result
                .onFailure {
                    assertTrue(result.isFailure)
                    assertEquals("Network error: 400", it.message)
                }
        }
    }

    @Test
    fun shouldReturnFailureWhenGetCompanyInfoThrowsException() = runTest {
        coEvery { spaceXApiServiceKtor.getCompanyInfo() }.throws(Exception("400"))

        repository.getCompanyInfo().collect { result ->
            result
                .onFailure {
                    assertTrue(result.isFailure)
                    assertEquals("Unknown API error: 400", it.message)
                }
        }
    }

    @Test
    fun shouldFetchAndStoreALlSpaceItemInDatabase() = runTest {
        val expected = listOf(TestData.allSpaceXLaunchesResponseItem)
        coEvery { spaceXApiServiceKtor.getAllLaunches() } returns Response.success(expected)

        val actual = repository.fetchAndStoreALlSpaceItemLaunches()

        actual.onSuccess {
            assertTrue(it)
        }

        coVerify(exactly = ONCE) {
            spaceXDao.insertSpaceXLaunchItem(expected.map { it.toSpaceXLaunchItemEntity() })
        }
    }


    @Test
    fun shouldReturnFailureWhenFetchAndStoreALlSpaceItemFailsWith400() = runTest {

        val expected = "Get all launches failed: - 400"

        coEvery { spaceXApiServiceKtor.getAllLaunches() } returns Response.error(
            400,
            expected.toResponseBody("application/json".toMediaType())
        )

        val actual = repository.fetchAndStoreALlSpaceItemLaunches()

        actual.onFailure {
            assertTrue(actual.isFailure)
            assertEquals(expected, it.message)
        }

        coVerify(exactly = ZERO) {
            spaceXDao.insertSpaceXLaunchItem(any())
        }
    }

    @Test
    fun shouldReturnFailureWhenFetchAndStoreALlSpaceItemThrowsIOException() = runTest {
        val expected = "Network error: 400"
        coEvery { spaceXApiServiceKtor.getAllLaunches() }.throws(IOException("400"))

        val actual = repository.fetchAndStoreALlSpaceItemLaunches()

        actual.onFailure {
            assertTrue(actual.isFailure)
            assertEquals(expected, it.message)
        }

        coVerify(exactly = ZERO) {
            spaceXDao.insertSpaceXLaunchItem(any())
        }
    }

    @Ignore("This test is failing due to an issue with paging 3: https://issuetracker.google.com/issues/331684448")
    @Test
    fun shouldReturnAPagingDataFromDatabase() = runTest {
        val expected = listOf(TestData.allSpaceXLaunchesResponseItem.toSpaceXLaunchItemEntity())


        val pagingSource: PagingSource<Int, SpaceXLaunchItemEntity> = mockk(relaxed = true)
        coEvery { pagingSource.load(any()) } returns PagingSource.LoadResult.Page(
            data = expected,
            prevKey = null,
            nextKey = null
        )
        coEvery { spaceXDao.getSpaceXLaunchItem() } returns pagingSource

        repository.getSpaceXLaunchItem().collect {
            it.map { result: SpaceXLaunchItem ->
                assertEquals(expected[0].missionName, result.missionName)
            }
        }


        coVerify(exactly = ONCE) {
            spaceXDao.insertSpaceXLaunchItem(any())
        }
    }


    private companion object {
        const val ONCE = 1
        const val ZERO = 0
    }
}