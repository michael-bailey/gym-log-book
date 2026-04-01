plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.serialisation)
	alias(libs.plugins.krpc)
	alias(libs.plugins.ktorServer)
	alias(libs.plugins.koinCompiler)
}

kotlin {
	jvmToolchain(21)
}

application {
	mainClass = "net.michael_bailey.gym_log_book.server.MainKt"
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
