package com.mindera.rocketscience.spacex_domain.usecase

import com.mindera.rocketscience.spacex_domain.model.CompanyInfo
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Use case for retrieving the company info. */
class GetCompanyInfoUseCase @Inject constructor(
    private val repository: SpaceXRepository
) {
    operator fun invoke(): Flow<Result<CompanyInfo>> = repository.getCompanyInfo()
}