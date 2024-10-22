package com.mindera.rocketscience.spacex_domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilterAllLaunchUseCase(
    private val repository: SpaceXRepository
) {
    operator fun invoke(
        year: Int?,
        onlySuccessfulLaunch: Boolean
    ): Flow<PagingData<SpaceXLaunchItem>> {
        return repository.getSpaceXLaunchItem()
            .map { pagingData: PagingData<SpaceXLaunchItem> ->
                pagingData.filter { launch ->
                    (year == null || launch.year == year.toString()) &&
                            launch.success == onlySuccessfulLaunch
                }
            }
    }
}