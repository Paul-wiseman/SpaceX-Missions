package com.mindera.rocketscience.spacex_data.remote.service

import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.dto.CompanyInfoResponse

interface SpaceXApiServiceKtor {
    //    @GET("${BuildConfig.BASE_URL}${BuildConfig.COMPANY_INFO}") // for retrofit
    suspend fun getCompanyInfo(): CompanyInfoResponse

    //    @GET("${BuildConfig.BASE_URL}${BuildConfig.GET_ALL_LAUNCHES}") // for retrofit
    suspend fun getAllLaunches(): List<AllSpaceXLaunchesResponseItem>
}