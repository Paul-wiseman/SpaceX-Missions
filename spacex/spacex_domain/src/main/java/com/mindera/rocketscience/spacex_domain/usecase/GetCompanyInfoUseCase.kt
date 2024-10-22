package com.mindera.rocketscience.spacex_domain.usecase

import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow

/** Use case for retrieving the company info. */
class GetCompanyInfoUseCase(
    private val repository: SpaceXRepository
) {
    operator fun invoke(): Flow<Result<CompanyInfo>> = repository.getCompanyInfo()
}