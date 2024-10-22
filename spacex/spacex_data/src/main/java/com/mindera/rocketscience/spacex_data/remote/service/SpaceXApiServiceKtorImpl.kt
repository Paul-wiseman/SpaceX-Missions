package com.mindera.rocketscience.spacex_data.remote.service

import com.mindera.rocketscience.spacex_data.BuildConfig
import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.dto.CompanyInfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.path

class SpaceXApiServiceKtorImpl(
    private val client: HttpClient
) : SpaceXApiServiceKtor {
    var missionName: String = ""
        get() = field
        private set(value) {
            field = "new value $value"
        }

    override suspend fun getCompanyInfo(): CompanyInfoResponse {
        return client.get {
            url {
                apiUrl(BuildConfig.COMPANY_INFO)
            }
        }.body<CompanyInfoResponse>()
    }

    override suspend fun getAllLaunches(): List<AllSpaceXLaunchesResponseItem> {
        return client.get {
            url {
                apiUrl(BuildConfig.GET_ALL_LAUNCHES)
            }
        }.body()
    }

    private fun URLBuilder.apiUrl(path: String) {
        protocol = URLProtocol.HTTPS
        host = BuildConfig.BASE_URL
        path(path)
    }

}