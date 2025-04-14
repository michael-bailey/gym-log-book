plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("com.google.dagger.hilt.android")
	id("org.jetbrains.kotlin.plugin.serialization")
	id("org.jetbrains.kotlin.plugin.compose")
}

kapt {
	correctErrorTypes = true
}

android {
	namespace = "com.example.myapplication"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.example.myapplication"
		minSdk = 33
		targetSdk = 34
		versionCode = 1
		versionName = "1.0"

	}

	buildTypes {
		release {
			isMinifyEnabled = false
			setProguardFiles(
				listOf(
					getDefaultProguardFile("proguard-android-optimize.txt"),
					"proguard-rules.pro"
				)
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
}

dependencies {

	val compose_version = "1.7.8"
	val room_version = "2.7.0"
	val nav_version = "2.8.9"
	val gson_version = "2.9.0"
	val work_version = "2.10.0"
	val security_version = "1.0.0"
	val hilt_version = "2.56.1"

	implementation(project(":gym_library"))

	// android deps
	implementation("androidx.core:core-ktx:1.16.0")
	implementation("androidx.core:core-splashscreen:1.0.1")
	implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
	implementation("androidx.lifecycle:lifecycle-livedata-ktx")

	// google play deps
	implementation("com.google.android.gms:play-services-wearable:19.0.0")

	// compose deps
	implementation(platform("androidx.compose:compose-bom:2025.04.00"))

	implementation("androidx.compose.ui:ui")
	implementation("androidx.compose.ui:ui-graphics")
	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.wear.compose:compose-material:1.4.1")
	implementation("androidx.wear.compose:compose-foundation:1.4.1")

	implementation("androidx.compose.ui:ui-tooling-preview")
	implementation("androidx.compose.material3:material3")
	implementation("androidx.compose.runtime:runtime-livedata")

	implementation("androidx.activity:activity-compose:1.10.1")
	implementation("androidx.compose.material:material-icons-extended:$compose_version")

	// tile deps
	implementation("androidx.wear.tiles:tiles:1.4.1")
	implementation("androidx.wear.tiles:tiles-material:1.4.1")
	implementation("androidx.wear.tiles:tiles-tooling-preview:1.4.1")

	// horologist deps
	implementation("com.google.android.horologist:horologist-compose-tools:0.6.23")
	implementation("com.google.android.horologist:horologist-tiles:0.6.23")
	implementation("androidx.wear.watchface:watchface-complications-data-source-ktx:1.2.1")

	// compose navigation
	implementation("androidx.navigation:navigation-compose:$nav_version")
	implementation("androidx.navigation:navigation-runtime-ktx:$nav_version")

	// room database deps
	implementation("androidx.room:room-runtime:$room_version")
	implementation("androidx.room:room-ktx:$room_version")
	kapt("androidx.room:room-compiler:$room_version")

	// hilt deps
	implementation("com.google.dagger:hilt-android:$hilt_version")
	kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
	implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

	// debug deps
	implementation("androidx.wear:wear-tooling-preview:1.0.0")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	debugImplementation("androidx.wear.tiles:tiles-tooling:1.4.1")

	// test deps
	androidTestImplementation(platform("androidx.compose:compose-bom:2025.04.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
