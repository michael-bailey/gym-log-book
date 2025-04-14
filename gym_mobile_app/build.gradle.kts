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

	val compose_version = "1.7.8"
	val room_version = "2.7.0"
	val nav_version = "2.8.9"
	val gson_version = "2.9.0"
	val work_version = "2.10.0"
	val security_version = "1.0.0"
	val hilt_version = "2.56.1"

	implementation(project(":gym_library"))

	// reflection
	implementation(kotlin("reflect", version = "2.1.20"))

	// android deps
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.androidx.constraintlayout)
	implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
	implementation("androidx.navigation:navigation-ui-ktx:$nav_version")


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

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
	androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
	debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
	debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
	testImplementation("org.mockito:mockito-core:5.17.0")
}