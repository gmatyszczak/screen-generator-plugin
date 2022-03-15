
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
    implementation("com.google.dagger:dagger:2.41")
    kapt("com.google.dagger:dagger-compiler:2.41")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("app.cash.turbine:turbine:0.7.0")
}

configurations {
    compileClasspath.get().resolutionStrategy.sortArtifacts(ResolutionStrategy.SortOrder.DEPENDENCY_FIRST)
    testCompileClasspath.get().resolutionStrategy.sortArtifacts(ResolutionStrategy.SortOrder.DEPENDENCY_FIRST)
}

intellij {
    version.set("2021.1.3")
    plugins.set(listOf("java", "org.jetbrains.kotlin"))
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

    withType<Test> {
        useJUnitPlatform()
    }
}
