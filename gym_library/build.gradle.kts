plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("kotlin-kapt")
	id("com.google.dagger.hilt.android")
	id("org.jetbrains.kotlin.plugin.serialization")
	id("org.jetbrains.kotlin.plugin.compose")
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

	val compose_version = "1.7.8"
	val room_version = "2.7.0"
	val nav_version = "2.8.9"
	val gson_version = "2.9.0"
	val work_version = "2.10.0"
	val security_version = "1.0.0"
	val hilt_version = "2.56.1"

	implementation("androidx.core:core-ktx:1.16.0")
	implementation("androidx.appcompat:appcompat:1.7.0")
	implementation("com.google.android.material:material:1.12.0")

	// room database deps
	implementation("androidx.room:room-runtime:$room_version")
	implementation("androidx.room:room-ktx:$room_version")
	kapt("androidx.room:room-compiler:$room_version")

	// hilt deps
	implementation("com.google.dagger:hilt-android:$hilt_version")
	kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
	implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

	// serialisation
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

	testImplementation("junit:junit:4.13.2")
	androidTestImplementation("androidx.test.ext:junit:1.2.1")
	androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}