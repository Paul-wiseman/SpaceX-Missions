package com.mindera.rocketscience.spacex_data.di

import android.content.Context
import androidx.room.Room
import com.mindera.rocketscience.spacex_data.local.database.SpaceXDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SpaceXDataBase {
        return Room.databaseBuilder(
            context,
            SpaceXDataBase::class.java,
            DATABASE_NAME
        ).build()
    }

    private const val DATABASE_NAME = "spacex_db"
}