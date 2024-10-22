//package com.mindera.rocketscience.spacex_data.di
//
//import android.content.Context
//import android.util.Log
//import com.itkacher.okprofiler.BuildConfig
//import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
//import com.mindera.rocketscience.spacex_data.local.database.SpaceXDataBase
//import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceKtor
//import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceKtorImpl
//import com.mindera.rocketscience.spacex_data.remote.service.SpaceXApiServiceRetrofit
//import com.mindera.rocketscience.spacex_data.repository.SpaceXRepositoryImpl
//import com.mindera.rocketscience.spacex_data.util.ConnectivityInterceptor
//import com.mindera.rocketscience.spacex_domain.repository.SpaceXRepository
//import com.mindera.rocketscience.util.NetworkUtil
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import io.ktor.client.HttpClient
//import io.ktor.client.engine.android.Android
//import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
//import io.ktor.client.plugins.logging.LogLevel
//import io.ktor.client.plugins.logging.Logger
//import io.ktor.client.plugins.logging.Logging
//import io.ktor.serialization.kotlinx.json.json
//import kotlinx.serialization.ExperimentalSerializationApi
//import kotlinx.serialization.json.Json
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.kotlinx.serialization.asConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Singleton
//    @Provides
//    fun providesOkhttpClient(
//        @ApplicationContext context: Context,
//        netWorkUtil: NetworkUtil,
//    ): OkHttpClient {
//        val client = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG) {
//            client.addInterceptor(OkHttpProfilerInterceptor())
//        }
//        return client.addInterceptor(
//            HttpLoggingInterceptor().apply {
//                setLevel(HttpLoggingInterceptor.Level.BODY)
//            })
//            .addInterceptor(ConnectivityInterceptor(context, netWorkUtil))
//            .build()
//    }
//
//    @OptIn(ExperimentalSerializationApi::class)
//    @Provides
//    @Singleton
//    fun providesKtorClient(): HttpClient {
//        return HttpClient(Android) {
//            engine { }
//            install(Logging) {
//                logger = object : Logger {
//                    override fun log(message: String) {
//                        Log.d("Ktor", message)
//                    }
//
//                }
//                level = LogLevel.BODY
//                Logging
//            }
//            install(ContentNegotiation) {
//                json(Json {
//                    prettyPrint = true
//                    isLenient = true
//                    ignoreUnknownKeys = true
//                    explicitNulls = false
//                })
//            }
//        }
//    }
//
//    @Provides
//    @Singleton
//    fun providesRetrofit(
//        okHttpClient: OkHttpClient
//    ): Retrofit {
//        val contentType = "application/json".toMediaType()
//        val json = Json {
//            ignoreUnknownKeys = true
//            isLenient = true
//        }
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(com.mindera.rocketscience.spacex_data.BuildConfig.BASE_URL)
//            .addConverterFactory(json.asConverterFactory(contentType))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun providesSpaceXApiServiceRetrofit(retrofit: Retrofit): SpaceXApiServiceRetrofit =
//        retrofit.create(SpaceXApiServiceRetrofit::class.java)
//
//    @Singleton
//    fun providesSpaceXApiServiceKtor(client: HttpClient): SpaceXApiServiceKtor =
//        SpaceXApiServiceKtorImpl(client)
//
//    @Provides
//    @Singleton
//    fun providesSpaceXRepository(
//        spaceXDataBase: SpaceXDataBase,
//        spaceXApiServiceRetrofit: SpaceXApiServiceRetrofit
//    ): SpaceXRepository =
//        SpaceXRepositoryImpl(spaceXDataBase.dao, spaceXApiServiceRetrofit)
//}