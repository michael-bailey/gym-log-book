import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.krpc)
}

kotlin {
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
