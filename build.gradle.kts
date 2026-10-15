// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}

// Kotlin 1.8 merged the jdk7 and jdk8 artifacts into kotlin-stdlib.
// Pin the old artifacts to the same version to avoid duplicate classes.
subprojects {
    configurations.configureEach {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin" &&
                (requested.name == "kotlin-stdlib-jdk7" || requested.name == "kotlin-stdlib-jdk8")
            ) {
                useVersion("1.8.10")
            }
        }
    }
}
