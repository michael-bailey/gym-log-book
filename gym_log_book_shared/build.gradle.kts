import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.androidLibrary)
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.krpc)
}

kotlin {
	androidTarget()
	jvm()

	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		browser()
	}

	sourceSets {
		val commonMain by getting
		val commonTest by getting

		commonMain.dependencies {
			api(libs.bundles.shared.contract)
		}

		commonTest.dependencies {
			implementation(libs.kotlin.test)
			implementation(libs.kotlin.testCoroutines)
		}
	}

	jvmToolchain(21)
}

android {
	namespace = "net.michael_bailey.gym_log_book.shared"
	compileSdk = 35

	defaultConfig {
		minSdk = 24
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}
}
