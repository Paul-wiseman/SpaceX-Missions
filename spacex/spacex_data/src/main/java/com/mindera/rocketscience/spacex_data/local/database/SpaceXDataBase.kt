package com.mindera.rocketscience.spacex_data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mindera.rocketscience.spacex_data.local.entity.SpaceXLaunchItemEntity

@Database(entities = [SpaceXLaunchItemEntity::class], version = 1, exportSchema = false)
abstract class SpaceXDataBase : RoomDatabase() {
    abstract val dao: SpaceXDao
}