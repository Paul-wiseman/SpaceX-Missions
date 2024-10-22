package com.mindera.rocketscience.spacex_domain.usecase

import androidx.paging.PagingData
import com.mindera.rocketscience.spacex_domain.model.SpaceXLaunchItem
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving a paginated list of SpaceX launches.
 */
class GetAllLaunchUseCase @Inject constructor(
    private val repository: SpaceXRepository
) {
    operator fun invoke(): Flow<PagingData<SpaceXLaunchItem>> {
        return repository.getSpaceXLaunchItem()
    }
}