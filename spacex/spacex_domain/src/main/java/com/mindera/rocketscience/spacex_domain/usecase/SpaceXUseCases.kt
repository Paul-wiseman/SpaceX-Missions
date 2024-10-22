package com.mindera.rocketscience.spacex_domain.usecase

/**
 * [SpaceXUseCases] is a wrapper class that holds all the use cases for the SpaceX module.
 */
data class SpaceXUseCases(
    val getCompanyInfoUseCase: GetCompanyInfoUseCase,
    val getAllLaunchUseCase: GetAllLaunchUseCase,
    val fetchAndStoreAllLaunchesUseCase: FetchAndStoreAllLaunchesUseCase,
    val filterAllLaunchUseCase: FilterAllLaunchUseCase
)
