plugins {
    id("com.android.application")
}

android {
    namespace = "com.wubinkkk.st01offline"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.wubinkkk.st01offline"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
