package com.example.plugins

import com.example.utils.DateSerializer
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

fun Application.configureSerialization() {

    // ContentNegotiation plugin 초기화
    install(ContentNegotiation) {
        // json serializer 활성화
        json(
            Json {
                useAlternativeNames = true
                prettyPrint = true

                // serializersModule 을 사용하여 기본 serializer에서 지원하지 않는 serializer 를 등록할 수 있다.
                serializersModule = SerializersModule {
                    // serializersModule 에서 사용할 serializer 를 등록
                    contextual(DateSerializer)
                }

            }
        )
    }


    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
