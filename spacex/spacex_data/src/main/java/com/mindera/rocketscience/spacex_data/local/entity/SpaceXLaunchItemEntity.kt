package com.mindera.rocketscience.spacex_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mindera.rocketscience.spacex_data.util.Constants.TABLE_NAME
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = TABLE_NAME)
data class SpaceXLaunchItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val missionName: String,
    val date: String,
    val year:String,
    val rocket: String,
    val rocketName: String,
    val imageLink: String,
    val success: Boolean,
    val wikipediaLink: String?,
    val articleLink: String?
)
