package com.mindera.rocketscience.spacex_data

import com.mindera.rocketscience.spacex_data.remote.dto.AllSpaceXLaunchesResponseItem
import com.mindera.rocketscience.spacex_data.remote.dto.CompanyInfoResponse
import com.mindera.rocketscience.spacex_data.remote.dto.Flickr
import com.mindera.rocketscience.spacex_data.remote.dto.Headquarters
import com.mindera.rocketscience.spacex_data.remote.dto.Links
import com.mindera.rocketscience.spacex_data.remote.dto.LinksX
import com.mindera.rocketscience.spacex_data.remote.dto.Patch

object TestData {
    val companyInfoResponse = CompanyInfoResponse(
        ceo = "vivendo",
        coo = "maximus",
        cto = "elaboraret",
        cto_propulsion = "latine",
        employees = 5375,
        founded = 6157,
        founder = "moderatius",
        headquarters = Headquarters(
            address = "finibus",
            city = "Durview",
            state = "South Dakota"
        ),
        id = "errem",
        launch_sites = 8208,
        links = Links(
            elon_twitter = "voluptatibus",
            flickr = "nulla",
            twitter = "unum",
            website = "homero"
        ),
        name = "Herschel Wood",
        summary = "iuvaret",
        test_sites = 6803,
        valuation = 7372,
        vehicles = 4593

    )

    val allSpaceXLaunchesResponseItem = AllSpaceXLaunchesResponseItem(
        auto_update = null,
        capsules = listOf(),
        crew = listOf(),
        date_local = "dicam",
        date_precision = null,
        date_unix = 4383,
        date_utc = null,
        flight_number = 9415,
        id = null,
        launchpad = null,
        links = LinksX(
            article = null, flickr = Flickr(
                original = listOf(),
                small = listOf()
            ), patch = Patch(
                large = null,
                small = null
            ), presskit = null, webcast = null, wikipedia = null, youtube_id = null
        ),
        name = "Erin Leblanc",
        net = null,
        payloads = listOf(),
        rocket = "intellegebat",
        ships = listOf(),
        success = null,
        tbd = null,
        upcoming = null
    )
}