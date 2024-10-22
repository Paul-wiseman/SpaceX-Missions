package com.mindera.rocketscience.di

import com.mindera.rocketscience.preference.SpaceXPreference
import com.mindera.rocketscience.preference.SpaceXPreferenceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val coreKoinModule = module {

    single<SpaceXPreference> { SpaceXPreferenceImpl(androidContext()) }
    single {

    }
}