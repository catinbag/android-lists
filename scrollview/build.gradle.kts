plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "org.demo.lists.scrollview"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.demo.lists.scrollview"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":core"))
    implementation(libs.appcompat)
    implementation(libs.material)
}
