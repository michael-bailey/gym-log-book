plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.hilt)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.compose)

	id("kotlin-kapt")
}

kapt {
	correctErrorTypes = true
}

android {
	namespace = "org.british_information_technologies.gym_log_book"
	compileSdk = 35

	defaultConfig {
		applicationId = "org.british_information_technologies.gym_log_book"
		minSdk = 33
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
		kapt {
			arguments {
				arg("room.schemaLocation", "$projectDir/schemas")
			}
		}
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
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
	kotlinOptions {
		jvmTarget = "17"
	}
	buildFeatures {
		viewBinding = true
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.10"
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {

	implementation(project(":gym_library"))

	// android deps
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.navigation.fragment.ktx)
	implementation(libs.navigation.ui.ktx)

	// compose deps
	implementation(platform(libs.compose.bom))
	implementation(libs.bundles.compose)
	debugImplementation(libs.compose.ui.tooling.preview)

	// material deps
	implementation(libs.compose.material)

	// lifecycle deps
	implementation(libs.bundles.lifecycle)

	// material design
	implementation(libs.material)
	implementation(libs.bundles.material.design)

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

	// security deps
	implementation(libs.android.crypto)

	// serialisation
	implementation(libs.serialization.json)
	implementation(libs.datetime)

	// bcrypt deps
	implementation(libs.jbcrypt)

	testImplementation(libs.junit)
	androidTestImplementation(libs.junit.android)
	androidTestImplementation(libs.espresso.core)
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	testImplementation(libs.mockito.core)
}