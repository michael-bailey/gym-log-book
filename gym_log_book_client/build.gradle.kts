import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
	alias(libs.plugins.kotlinMultiplatform)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.krpc)
	alias(libs.plugins.composeMultiplatform)
	alias(libs.plugins.composeCompiler)
}

kotlin {

	@OptIn(ExperimentalKotlinGradlePluginApi::class)
	jvm("desktop") {
		mainRun {
			mainClass.set("net.michael_bailey.gym_log_book.client.MainKt")
		}
	}

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
		val commonTest by getting
		val desktopMain by getting
		val wasmJsMain by getting

		commonMain.dependencies {
			implementation(project(":gym_log_book_shared"))
			implementation(compose.runtime)
			implementation(compose.foundation)
			implementation(compose.material3)
			implementation(compose.ui)
			implementation(libs.bundles.ktor.client.common)
		}

		commonTest.dependencies {
			implementation(libs.kotlin.test)
			implementation(libs.kotlin.testCoroutines)
		}

		desktopMain.dependencies {
			implementation(compose.desktop.currentOs)
			implementation(libs.bundles.ktor.client.jvm)
		}

		wasmJsMain.dependencies {
			implementation(libs.bundles.ktor.client.wasm)
		}
	}

	jvmToolchain(21)

}

compose.desktop {
	application {
		mainClass = "net.michael_bailey.gym_log_book.client.MainKt"
	}
}

val desktopFatJar by tasks.registering(Jar::class) {
	group = "distribution"
	description = "Assembles a runnable desktop jar with all runtime dependencies."
	archiveClassifier.set("all")
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	manifest {
		attributes["Main-Class"] = "net.michael_bailey.gym_log_book.client.MainKt"
	}

	dependsOn("desktopJar")

	val desktopJar = tasks.named("desktopJar", Jar::class)
	from({
		zipTree(desktopJar.get().archiveFile.get().asFile)
	})
	from({
		configurations.getByName("desktopRuntimeClasspath").map { dependency ->
			if (dependency.isDirectory) dependency else zipTree(dependency)
		}
	})
}
