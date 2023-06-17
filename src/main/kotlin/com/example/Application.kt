package com.example

import com.example.plugins.configureMonitoring
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {

    val connectionString = ConnectionString("mongodb+srv://daehyeon:wfoP1nMmYNj6tulU@cluster.93ajdtg.mongodb.net/test")
    val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
    val client = MongoClients.create(settings)
    environment.monitor.subscribe(ApplicationStarted) {
        println("MongoDB client connected.")
    }

    environment.monitor.subscribe(ApplicationStopping) {
        client.close()
        println("MongoDB client disconnected.")
    }

    // serialization plugin 초기화
    configureSerialization()
    // Routing plugin 초기화
    configureRouting(client)
    // Monitoring plugin 초기화
    configureMonitoring()
}