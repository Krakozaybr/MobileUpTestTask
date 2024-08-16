plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {

    api(libs.kotlin.coroutines.core)

    api(platform(libs.koin.bom))
    api(libs.koin.core)

    api(libs.immutable.collections)

}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}