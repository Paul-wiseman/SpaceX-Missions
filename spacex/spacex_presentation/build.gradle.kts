plugins {
    `android-library`
    `kotlin-android`
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "com.mindera.rocketscience.spacex_presentation"
}

dependencies {
    implementation(project(Modules.SpacexDomain))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.core))
}