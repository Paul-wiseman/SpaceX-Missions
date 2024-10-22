package com.mindera.rocketscience.spacex_domain.repository

import androidx.paging.PagingData
import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import kotlinx.coroutines.flow.Flow

interface SpaceXRepository {
    fun getCompanyInfo(): Flow<Result<CompanyInfo>>

    suspend fun fetchAndStoreALlSpaceItemLaunches():Result<Boolean>

    fun getSpaceXLaunchItem(): Flow<PagingData<SpaceXLaunchItem>>
}