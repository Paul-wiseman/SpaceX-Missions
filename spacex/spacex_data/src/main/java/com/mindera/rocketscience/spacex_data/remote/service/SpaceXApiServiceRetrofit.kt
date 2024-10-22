package com.mindera.rocketscience.spacex_data.remote.service

import com.mindera.rocketscience.spacex_data.BuildConfig
import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.dto.CompanyInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface SpaceXApiServiceRetrofit {
        @GET("${BuildConfig.BASE_URL}${BuildConfig.COMPANY_INFO}") // for retrofit
    suspend fun getCompanyInfo(): Response<CompanyInfoResponse>

        @GET("${BuildConfig.BASE_URL}${BuildConfig.GET_ALL_LAUNCHES}") // for retrofit
    suspend fun getAllLaunches(): Response<List<AllSpaceXLaunchesResponseItem>>
}