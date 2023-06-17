package com.example.plugins

import com.example.routes.userRouting
import com.mongodb.client.MongoClient
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(mongoClient: MongoClient) {


    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        userRouting(mongoClient)
    }
}
