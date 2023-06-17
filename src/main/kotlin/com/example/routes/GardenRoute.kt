package com.example.routes

import com.example.logger
import com.example.models.garden.GardenModel
import com.example.models.response.ResponseModel
import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters.eq
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.conversions.Bson
import org.bson.types.ObjectId


fun Route.gardenRouting(mongoClient: MongoClient) {
    val database = mongoClient.getDatabase("soil-analysis")

    // garden 컬렉션을 가져온다.
    val collection = database.getCollection("garden")

    //garden/{userId}로 요청이 들어오면 userId를 파라미터로 받아서 garden 컬렉션에서 createdBy의 ObjectId와 일치하는 데이터를 조회한다.
    get("/garden/{userId}") {

        // path parameter로 userId를 받는다.
        val userId = call.parameters["userId"]

        // userId가 null이면 요청이 잘못된 것이므로 400 Bad Request를 반환한다.
        if (userId == null) {
            call.respondText("요청이 잘못 되었습니다. 다시 확인해주십시요.", status = HttpStatusCode.BadRequest)

        } else {

            // garden 컬렉션에서 createdBy의 ObjectId와 일치하는 데이터를 조회한다.
            // eq() 함수는 Document의 필드와 해당 값이 "일치"하는지 확인하는 함수이다.
            val filter: Bson = eq("createdBy", ObjectId(userId))

            // 생성한 filter를 사용하여 garden 컬렉션에서 데이터를 조회하고 map을 사용하여 GardenModel로 변환한다.
            val result = collection.find(filter).toList().map {
                GardenModel.fromDocument(it)
            }.toList()


            // 조회한 데이터를 ResponseModel에 담아서 반환한다.
            call.respond(HttpStatusCode.OK, ResponseModel(data = result, message = "조회 성공"))
        }
    }

    // garden 컬렉션에 데이터를 등록한다.
    post("/garden") {
        try {

            // 요청받은 데이터를 GardenModel로 변환한다.
            val newGarden = call.receive<GardenModel>()

            // garden 컬렉션에 데이터를 등록한다. .toDocument() 함수를 사용하여 GardenModel을 Document로 변환한다.
            val result = collection.insertOne(newGarden.toDocument())

            logger.info("result : $result")

            // 등록한 데이터를 ResponseModel에 담아서 반환한다.
            call.respond(HttpStatusCode.OK, ResponseModel(data = null, message = "정상적으로 등록되었습니다."))

        } catch (e: Exception) {

            logger.error(e.message)

            // 요청이 잘못된 경우 400 Bad Request를 반환한다.
            call.respond(HttpStatusCode.BadRequest, ResponseModel(data = null, message = "요청이 잘못 되었습니다. 다시 확인해주십시요."))
        }


    }
}