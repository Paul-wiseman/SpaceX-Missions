package com.mindera.rocketscience.spacex_domain.usecase

import app.cash.turbine.test
import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import com.mindera.rocketscience.spacex_domain.test_data.TestData
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCompanyInfoUseCaseTest {

    private lateinit var getCompanyInfoUseCase: GetCompanyInfoUseCase
    private val repository: SpaceXRepository = mockk()

    @Before
    fun setUp() {
        getCompanyInfoUseCase = GetCompanyInfoUseCase(repository)
    }

    @Test
    fun shouldGetCompanyInfoFromRepository() = runTest {
        val expectedCompanyInfo = Result.success(TestData.companyInfo)

        val expectedFlow = flowOf(expectedCompanyInfo)

        every { repository.getCompanyInfo() } returns expectedFlow

        getCompanyInfoUseCase().test {
            assertEquals(expectedCompanyInfo, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun shouldReturnErrorResultWhenRepositoryThrowsException() = runTest {
        val expectedError = Result.failure<CompanyInfo>(Exception("Error"))
        val expectedFlow = flowOf(expectedError)

        every { repository.getCompanyInfo() } returns expectedFlow

        getCompanyInfoUseCase().test {
            assertEquals(expectedError, awaitItem())
            awaitComplete()
        }
    }
}