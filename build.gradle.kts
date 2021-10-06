import org.jetbrains.intellij.ideaDir
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.jetbrains.intellij") version "1.2.0"
    id("idea")
    kotlin("kapt") version "1.5.31"
}

group = "screengenerator"
version = "1.2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.5.2")
    implementation("com.google.dagger:dagger:2.39.1")
    kapt("com.google.dagger:dagger-compiler:2.39.1")

    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:2.23.4")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}

intellij {
    version.set("2021.2")
    plugins.set(listOf("org.jetbrains.kotlin"))
    updateSinceUntilBuild.set(false)
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    runPluginVerifier {
        ideVersions.set(listOf("2020.3.4", "2021.1.3", "2021.2.1"))
    }

    runIde {
        ideaDir("/Applications/Android Studio.app")
    }
}
