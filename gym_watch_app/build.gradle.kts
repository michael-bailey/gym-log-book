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
	implementation(libs.androidx.core.ktx)
	implementation(libs.bundles.lifecycle)
	implementation(libs.androidx.core.splashscreen)

	// google play deps
	implementation(libs.play.services.wearable)

	// compose deps
	implementation(platform(libs.compose.bom))
	implementation(libs.bundles.compose)

	implementation(libs.wear.compose.material)
	implementation(libs.wear.compose.foundation)

	debugImplementation(libs.compose.ui.tooling.preview)
	implementation(libs.compose.material3)
	implementation(libs.compose.material.icons.extended)

	// tile deps
	implementation(libs.androidx.tiles)
	implementation(libs.androidx.tiles.material)
	implementation(libs.androidx.tiles.tooling.preview)

	// horologist deps
	implementation(libs.horologist.compose.tools)
	implementation(libs.horologist.tiles)
	implementation(libs.androidx.watchface.complications.data.source.ktx)

	// compose navigation
	implementation(libs.bundles.navigation)


	// room database deps
	implementation(libs.bundles.room)
	kapt(libs.room.compiler)

	// work manager deps
	implementation(libs.work.manager)

	// hilt deps
	implementation(libs.bundles.hilt)
	kapt(libs.hilt.compiler)

	// debug deps
	implementation("androidx.wear:wear-tooling-preview:1.0.0")
	debugImplementation("androidx.compose.ui:ui-tooling")
	debugImplementation("androidx.compose.ui:ui-test-manifest")
	debugImplementation("androidx.wear.tiles:tiles-tooling:1.4.1")

	// test deps
	androidTestImplementation(platform("androidx.compose:compose-bom:2025.04.00"))
	androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
