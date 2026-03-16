
plugins {
	alias(libs.plugins.kotlinMultiplatform) apply false
	alias(libs.plugins.kotlinJvm) apply false
	alias(libs.plugins.androidApplication) apply false
	alias(libs.plugins.androidLibrary) apply false
	alias(libs.plugins.hilt) apply false
	alias(libs.plugins.serialisation) apply false
	alias(libs.plugins.composeMultiplatform) apply false
	id("com.google.devtools.ksp") version "2.3.4" apply false
}

subprojects {
	group = "net.michael_bailey"
	version = "0.0.1"
}