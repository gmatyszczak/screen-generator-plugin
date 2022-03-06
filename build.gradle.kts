import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.intellij") version "1.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    kotlin("kapt") version "1.6.10"
}

group = "screengenerator"
version = "1.2.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.5.2")
    implementation("com.google.dagger:dagger:2.41")
    kapt("com.google.dagger:dagger-compiler:2.41")

    testImplementation("junit:junit:4.12")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}

intellij {
    version.set("2021.1.1")
    plugins.set(listOf("org.jetbrains.kotlin"))
    intellij.updateSinceUntilBuild.set(false)
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    runIde {
        ideDir.set(file("/Applications/Android Studio.app/Contents"))
    }

    runPluginVerifier {
        ideVersions.set(listOf("IU-182.5262.2"))
    }
}
