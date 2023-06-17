package com.example.routes

import com.example.models.response.ResponseModel
import com.example.models.user.UserLoginRequestModel
import com.example.models.user.UserModel
import com.example.models.user.UserRegisterModel
import com.mongodb.client.MongoClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.Document
import org.bson.conversions.Bson
import org.litote.kmongo.and
import org.litote.kmongo.findOne


fun Route.userRouting(mongoClient: MongoClient) {

    // soil-analysis라는 이름의 database를 가져온다.
    val database = mongoClient.getDatabase("soil-analysis")

    // user라는 이름의 collection을 가져온다.
    val collection = database.getCollection("user")

    // 로그인 요청
    post("/login") {

        // 요청의 body를 가져와서 UserLoginRequestModel로 변환
        val loginRequest = call.receive<UserLoginRequestModel>()

        // DB에서 username, password이 일치하는 유저를 찾는다.
        val filter: Bson = and(
            Document("username", loginRequest.userName),
            Document("password", loginRequest.password)
        )

        // filter를 가지고 document를 검색
        val result = collection.findOne(filter)

        // document가 존재하면 로그인 성공, 존재하지 않으면 로그인 실패
        if (result != null) {

            // document를 UserModel로 변환
            val user = UserModel.fromDocument(result)

            // 로그인 성공 및 User 정보 전송
            call.respond(HttpStatusCode.OK, ResponseModel(data = user, message = "로그인 성공"))
        } else {
            call.respond(HttpStatusCode.NotFound, ResponseModel(data = null, message = "유저를 찾을 수 없습니다."))
        }


    }

    // 회원가입 요청
    post("/register") {

        try {
            val requestBody = call.receive<UserRegisterModel>()
            print(requestBody)
            call.respond(HttpStatusCode.OK, ResponseModel(data = requestBody, message = "회원가입 성공"))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, ResponseModel(data = null, message = "잘못된 요청입니다."))
        }


    }
}