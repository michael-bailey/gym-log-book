plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.krpc)
	alias(libs.plugins.ktorServer)
}

kotlin {
	jvmToolchain(21)
}

application {
	mainClass = "io.ktor.server.netty.EngineMain"
}

dependencies {
	implementation(project(":gym_log_book_shared"))
	implementation(libs.bundles.ktor.server)
	implementation(libs.kotlinx.coroutines.core)

	testImplementation(libs.kotlin.test.junit)
	testImplementation(libs.kotlin.testCoroutines)
	testImplementation(libs.ktor.server.test.host)
	testImplementation(libs.bundles.ktor.client.jvm)
	testImplementation(libs.bundles.ktor.client.common)
}
