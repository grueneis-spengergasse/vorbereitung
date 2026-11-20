import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    java
}

group = "at.spengergasse"
version = "1.0-SNAPSHOT"

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }

    project {
        languageLevel = IdeaLanguageLevel(JavaVersion.VERSION_25)
    }
}

repositories {
    if (System.getProperty("os.name").lowercase().contains("windows")) {
        maven {
            url = uri("https://artifactory.htl-wien5.schule/#preview/browse/maven-public/")
            name = "Spengergasse Artifactory"
        }
    }
    else {
        mavenCentral()
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    implementation(libs.jackson.databind)

    testImplementation(platform(libs.junit.jupiter.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)

    testRuntimeOnly(libs.junit.platform.launcher)
}

val scratchRoot: java.io.File = run {
    val os = System.getProperty("os.name").lowercase()
    if (os.contains("windows"))
        File("C:/Scratch")
    else
        File("/tmp/spg-scratch")
}
val localBuildDirectoryRoot = scratchRoot.resolve("${rootProject.name}/${name}/build")
logger.info("Local build directory: ${localBuildDirectoryRoot.absolutePath}")
layout.buildDirectory.set(
    layout.dir(
        providers.provider {
            localBuildDirectoryRoot
        }
    )
)

tasks.test {
    useJUnitPlatform()
}
