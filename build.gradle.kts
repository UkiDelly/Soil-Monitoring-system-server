val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
	kotlin("jvm") version "1.8.22"
	id("io.ktor.plugin") version "2.3.1"
	id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"
}

group = "com.example"
version = "0.0.1"
application {
	mainClass.set("com.example.ApplicationKt")
	
	val isDevelopment: Boolean = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
	implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
	implementation("ch.qos.logback:logback-classic:$logback_version")
	implementation("io.ktor:ktor-server-call-logging:2.3.1-eap-678")
	implementation("io.ktor:ktor-server-call-logging-jvm:2.3.1")
	implementation("org.litote.kmongo:kmongo:4.8.0")
	testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}