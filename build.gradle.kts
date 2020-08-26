// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    apply(from = "./versions.gradle.kts")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        val kotlinVersion: String by project.extra
        val navigationVersion: String by project.extra
        classpath("com.android.tools.build:gradle:4.1.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
