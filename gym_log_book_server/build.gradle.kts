val koin_version: String by project
val koog_version: String by project
val kotlin_version: String = "2.20.0"
val logback_version: String by project

plugins {
	alias(libs.plugins.kotlinJvm)
	id("io.ktor.plugin") version "3.4.1"
}

application {
	mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
	jvmToolchain(21)
}

dependencies {
	implementation("io.ktor:ktor-server-core")
	implementation("io.insert-koin:koin-ktor:4.1.1")
	implementation("io.insert-koin:koin-logger-slf4j:4.1.1")
	implementation("ai.koog:koog-ktor:0.6.4")
	implementation("io.ktor:ktor-server-netty")
	implementation("ch.qos.logback:logback-classic:1.5.32")
	implementation("io.ktor:ktor-server-config-yaml")
	testImplementation("io.ktor:ktor-server-test-host")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.3.0")
}
