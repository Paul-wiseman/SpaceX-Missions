package com.mindera.rocketscience.spacex_domain.usecase

import androidx.paging.PagingData
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving a paginated list of SpaceX launches.
 */
class GetAllLaunchUseCase (
    private val repository: SpaceXRepository
) {
    operator fun invoke(): Flow<PagingData<SpaceXLaunchItem>> {
        return repository.getSpaceXLaunchItem()
    }
}