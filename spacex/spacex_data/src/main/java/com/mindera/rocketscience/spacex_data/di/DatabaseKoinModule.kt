package com.mindera.rocketscience.spacex_data.di

import androidx.room.Room
import com.mindera.rocketscience.spacex_data.local.database.SpaceXDao
import com.mindera.rocketscience.spacex_data.local.database.SpaceXDataBase
import com.mindera.rocketscience.spacex_data.util.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val spaceXDatabaseKoinModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            SpaceXDataBase::class.java,
            DATABASE_NAME
        ).build()
    }
    single<SpaceXDao> {
        get<SpaceXDataBase>().dao
    }

}