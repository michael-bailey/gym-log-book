plugins {
	alias(libs.plugins.android.library)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.hilt)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.compose)

	id("kotlin-kapt")
}

android {
	namespace = "org.british_information_technologies.gym_library"
	compileSdk = 35

	defaultConfig {
		minSdk = 33

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

	api(kotlin("reflect", version = "2.1.20"))

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.compose.material)

	implementation(platform(libs.compose.bom))

	// material design
	implementation(libs.material)
	implementation(libs.bundles.material.design)

	// room database deps
	api(libs.bundles.room)
	kapt(libs.room.compiler)

	// work manager deps
	api(libs.work.manager)

	// hilt deps
	api(libs.bundles.hilt)
	kapt(libs.hilt.compiler)

	// serialisation
	implementation(libs.serialization.json)
	implementation(libs.datetime)

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}