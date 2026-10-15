plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "org.demo.lists.rvlayouts"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.demo.lists.rvlayouts"
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
    implementation(libs.recyclerview)
}
