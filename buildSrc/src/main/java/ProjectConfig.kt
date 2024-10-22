import org.gradle.api.JavaVersion

object ProjectConfig {
    const val appId = "com.mindera.rocketscience"
    const val compileSdk = 34
    const val minSdk = 26
    const val targetSdk = 34
    const val versionCode = 1
    const val versionName = "1.0"
    const val jvmTarget = "11"
    val javersion = JavaVersion.VERSION_11
}