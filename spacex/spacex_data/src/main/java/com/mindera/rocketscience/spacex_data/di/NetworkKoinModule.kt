package com.mindera.rocketscience.spacex_data.di

import android.util.Log
import com.itkacher.okprofiler.BuildConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceKtor
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceKtorImpl
import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceRetrofit
import com.mindera.rocketscience.spacex_data.repository.SpaceXRepositoryImpl
import com.mindera.rocketscience.spacex_data.util.ConnectivityInterceptor
import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
import com.mindera.rocketscience.util.NetworkUtil
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


val networkModule = module {
    single {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }
        client.addInterceptor(
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })

            .addInterceptor(ConnectivityInterceptor(androidContext(), get()))
            .build()
    }

    single { NetworkUtil() }

    @OptIn(ExperimentalSerializationApi::class)
    single {
        HttpClient(Android) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("Ktor", message)
                    }

                }
                level = LogLevel.BODY
                Logging
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                })
            }
        }
    }


    single {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        Retrofit.Builder()
            .client(get())
            .baseUrl(com.mindera.rocketscience.spacex_data.BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }


    single<SpaceXApiServiceRetrofit> {
        get<Retrofit>().create(SpaceXApiServiceRetrofit::class.java)
    }



    single<SpaceXApiServiceKtor> { SpaceXApiServiceKtorImpl(get()) }

    single<SpaceXRepository> {
        SpaceXRepositoryImpl(get(), get())
    }
}