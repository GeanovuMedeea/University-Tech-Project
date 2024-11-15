plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.7.0" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Safe Args plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.0")
    }
}