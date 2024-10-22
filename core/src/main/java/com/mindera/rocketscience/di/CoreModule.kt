package com.mindera.rocketscience.di

import android.content.Context
import com.mindera.rocketscience.preference.SpaceXPreference
import com.mindera.rocketscience.preference.SpaceXPreferenceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideSpaceXPreference(
        @ApplicationContext context: Context
    ): SpaceXPreference = SpaceXPreferenceImpl(context)
}