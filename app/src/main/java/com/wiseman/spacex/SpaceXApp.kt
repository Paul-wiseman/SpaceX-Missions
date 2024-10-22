package com.wiseman.spacex

import android.app.Application
import com.mindera.rocketscience.di.coreKoinModule
import com.mindera.rocketscience.spacex_data.di.networkModule
import com.mindera.rocketscience.spacex_data.di.spaceXDatabaseKoinModule
import com.mindera.rocketscience.spacex_presentation.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

//import dagger.hilt.android.HiltAndroidApp

//@HiltAndroidApp
class SpaceXApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpaceXApp)
            modules(
                networkModule,
                spaceXDatabaseKoinModule,
                spaceXDatabaseKoinModule,
                viewModule,
                coreKoinModule
            )
        }
    }
}