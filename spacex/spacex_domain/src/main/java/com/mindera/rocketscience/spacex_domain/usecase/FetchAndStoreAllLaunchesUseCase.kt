package com.mindera.rocketscience.spacex_domain.usecase

import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import javax.inject.Inject

class FetchAndStoreAllLaunchesUseCase @Inject constructor(
    private val repository: SpaceXRepository
) {

    suspend operator fun invoke(): Result<Boolean> = repository.fetchAndStoreALlSpaceItemLaunches()
}