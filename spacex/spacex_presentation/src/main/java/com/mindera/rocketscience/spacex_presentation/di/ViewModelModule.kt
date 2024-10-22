package com.mindera.rocketscience.spacex_presentation.di

import com.mindera.rocketscience.spacex_domain.usecase.FetchAndStoreAllLaunchesUseCase
import com.mindera.rocketscience.spacex_domain.usecase.FilterAllLaunchUseCase
import com.mindera.rocketscience.spacex_domain.usecase.GetAllLaunchUseCase
import com.mindera.rocketscience.spacex_domain.usecase.GetCompanyInfoUseCase
import com.mindera.rocketscience.spacex_domain.usecase.SpaceXUseCases
import com.mindera.rocketscience.spacex_presentation.screen.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    single {
        SpaceXUseCases(
            getCompanyInfoUseCase = GetCompanyInfoUseCase(get()),
            getAllLaunchUseCase = GetAllLaunchUseCase(get()),
            fetchAndStoreAllLaunchesUseCase = FetchAndStoreAllLaunchesUseCase(get()),
            filterAllLaunchUseCase = FilterAllLaunchUseCase(get())
        )

    }

    viewModel {
        HomeViewModel(get(), get())
    }
}