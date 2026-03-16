import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
}

kotlin {

	jvm("desktop")

	@OptIn(ExperimentalWasmDsl::class)
	wasmJs {
		outputModuleName = "gymClient"
		browser {
			commonWebpackConfig {
				outputFileName = "gymClient.js"
			}
		}
		binaries.executable()
	}

	sourceSets {
		val desktopMain by getting

		commonMain.dependencies {
			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material3)
			implementation(compose.ui)
		}

		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
		}
	}

	jvmToolchain(21)

}

compose.desktop {
	application {
		mainClass = "MainKt"
	}
}
