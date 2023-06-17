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
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("RouteLogger")

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

    // MongoDB 주소
    val connectionString = ConnectionString("mongodb+srv://daehyeon:wfoP1nMmYNj6tulU@cluster.93ajdtg.mongodb.net/test")

    // MongoDB 설정하기
    // .builder()를 통해 MongoClientSettings.Builder를 생성한다.
    // .applyConnectionString()을 통해 ConnectionString을 설정한다.
    // .build()를 통해 MongoClientSettings 객체를 생성한다.
    val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()

    // settings를 가진 MongoDB 클라이언트를 생성한다.
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
    // Routing plugin 초기화 및 몽고디비 클라이언트를 넘겨준다.
    configureRouting(client)
    // Monitoring plugin 초기화
    configureMonitoring()
}