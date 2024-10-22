package com.mindera.rocketscience.spacex_data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AllSpaceXLaunchesResponseItem(
    val auto_update: Boolean?,
    val capsules: List<String>?,
    val crew: List<String>?,
    val date_local: String,
    val date_precision: String?,
    val date_unix: Int,
    val date_utc: String?,
    val flight_number: Int,
    val id: String?,
    val launchpad: String?,
    val links: LinksX,
    val name: String,
    val net: Boolean?,
    val payloads: List<String>?,
    val rocket: String,
    val ships: List<String>?,
    val success: Boolean?,
    val tbd: Boolean?,
    val upcoming: Boolean?,
)

@Serializable
data class LinksX(
    val article: String?,
    val flickr: Flickr,
    val patch: Patch,
    val presskit: String?,
    val webcast: String?,
    val wikipedia: String?,
    val youtube_id: String?
)

@Serializable
data class Patch(
    val large: String?,
    val small: String?
)

@Serializable
data class Flickr(
    val original: List<String>?,
    val small: List<String>?
)