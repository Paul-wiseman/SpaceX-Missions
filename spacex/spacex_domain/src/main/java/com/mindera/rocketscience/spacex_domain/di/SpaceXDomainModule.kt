//package com.mindera.rocketscience.spacex_domain.di
//
//import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
//import com.mindera.rocketscience.spacex_domain.usecase.FetchAndStoreAllLaunchesUseCase
//import com.mindera.rocketscience.spacex_domain.usecase.FilterAllLaunchUseCase
//import com.mindera.rocketscience.spacex_domain.usecase.GetCompanyInfoUseCase
//import com.mindera.rocketscience.spacex_domain.usecase.GetAllLaunchUseCase
//import com.mindera.rocketscience.spacex_domain.usecase.SpaceXUseCases
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//import dagger.hilt.android.scopes.ViewModelScoped
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object SpaceXDomainModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideSpaceXUseCase(repository: SpaceXRepository): SpaceXUseCases = SpaceXUseCases(
//        getCompanyInfoUseCase = GetCompanyInfoUseCase(repository),
//        getAllLaunchUseCase = GetAllLaunchUseCase(repository),
//        fetchAndStoreAllLaunchesUseCase = FetchAndStoreAllLaunchesUseCase(repository),
//        filterAllLaunchUseCase = FilterAllLaunchUseCase(repository)
//    )
//}