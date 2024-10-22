object Kotlin {
    const val version = "1.9.0"
    private const val serializationVersion = "1.6.3"
    const val serialzation = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
    const val kotlinSerializationConverter = "com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0"
    private const val ktorVersion = "2.3.12"
    const val ktorCore = "io.ktor:ktor-client-core:$ktorVersion"
    const val ktorAndroid = "io.ktor:ktor-client-android:$ktorVersion"
    const val ktorLogging = "io.ktor:ktor-client-logging:$ktorVersion"
    const val ktorConverter = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
    const val ktorContentNavigation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
}