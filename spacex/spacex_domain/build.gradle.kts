plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.mindera.rocketscience.spacex_domain"
}

dependencies {
    implementation(project(Modules.core))
    implementation(AndroidX.pagingRuntime)
    testImplementation(AndroidX.pagingRuntime)
    testImplementation(AndroidX.pagingCommon)
    testImplementation(Room.roomTesting)
}