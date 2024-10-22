plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.mindera.rocketscience.spacex_data"

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.spacexdata.com\"")
            buildConfigField("String", "COMPANY_INFO", "\"/v4/company\"")
            buildConfigField("String", "GET_ALL_LAUNCHES", "\"/v4/launches\"")
        }

        release {
            buildConfigField("String", "BASE_URL", "\"https://api.spacexdata.com\"")
            buildConfigField("String", "COMPANY_INFO", "\"/v4/company\"")
            buildConfigField("String", "GET_ALL_LAUNCHES", "\"/v4/launches\"")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}


dependencies {

    implementation(Retrofit.okHttp)
    implementation(Retrofit.retrofit)
    implementation(Retrofit.networkRequestProfiler)
    implementation(Retrofit.okHttpLoggingInterceptor)
    implementation(Kotlin.kotlinSerializationConverter)

    implementation(Kotlin.ktorAndroid)
    implementation(Kotlin.ktorCore)
    implementation(Kotlin.ktorConverter)
    implementation(Kotlin.ktorLogging)
    implementation(Kotlin.ktorContentNavigation)

    implementation(Kotlin.ktorConverter)

    implementation(project(Modules.SpacexDomain))
    implementation(project(Modules.core))


    "kapt"(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)
    implementation(Room.roomPaging)

    testImplementation(project(Modules.SpacexDomain))
}