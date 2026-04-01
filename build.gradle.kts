
plugins {
	alias(libs.plugins.kotlinAndroid) apply false
	alias(libs.plugins.kotlinMultiplatform) apply false
	alias(libs.plugins.kotlinJvm) apply false
	alias(libs.plugins.androidApplication) apply false
	alias(libs.plugins.androidLibrary) apply false
	alias(libs.plugins.hilt) apply false
	alias(libs.plugins.serialisation) apply false
	alias(libs.plugins.composeMultiplatform) apply false
	alias(libs.plugins.composeCompiler) apply false
	alias(libs.plugins.ksp) apply false
	alias(libs.plugins.krpc) apply false
	alias(libs.plugins.ktorServer) apply false
	alias(libs.plugins.koinCompiler) apply false
}

subprojects {
	group = "net.michael_bailey"
	version = "0.0.1"
}
