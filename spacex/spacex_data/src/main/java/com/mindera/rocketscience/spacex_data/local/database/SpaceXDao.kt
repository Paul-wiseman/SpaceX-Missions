package com.mindera.rocketscience.spacex_data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mindera.rocketscience.spacex_data.local.entity.SpaceXLaunchItemEntity

@Dao
interface SpaceXDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpaceXLaunchItem(spaceXLaunchItem: List<SpaceXLaunchItemEntity>)

    @Query("SELECT * FROM spacex_launch_item_entity ORDER BY id")
    fun getSpaceXLaunchItem(): PagingSource<Int, SpaceXLaunchItemEntity>
}