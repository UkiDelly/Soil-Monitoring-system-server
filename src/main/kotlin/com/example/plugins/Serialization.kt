package com.example.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSerialization() {

	// ContentNegotiation plugin 초기화
	install(ContentNegotiation) {
		// json serializer 활성화
		json()
	}
	routing {
		get("/json/kotlinx-serialization") {
			call.respond(mapOf("hello" to "world"))
		}
	}
}
