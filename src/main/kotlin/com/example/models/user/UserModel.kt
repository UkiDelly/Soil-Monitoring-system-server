package com.example.models.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.Document
import java.util.*


/**
 * com.example.models.user.User 모델
 * @property id id (몽고디비에서 사용하는 id)
 * @property name 이름
 * @property userName 사용자 이름
 * @property password 비밀번호
 * @property createAt 생성일
 */
@Serializable()
data class UserModel(

    // UserModel 클래스가 id의 주인이라는것을 알리기 위해 제너릭에 UserModel 클래스를 넣어준다.
    // @BsonId는 몽고디비에서 사용하는 id를 의미한다.
    val id: String?,
    val name: String,
    val userName: String,
    val password: String,
    @Contextual
    val createAt: Date


) {

    //    fromDocument를 구현하기 위해 companion object를 사용한다.
    companion object {

        // static 메소드와 같은 역할을 한다.
        fun fromDocument(document: Document): UserModel {

            // Document에서 추출하여 UserModel을 반환
            return UserModel(
                id = document.getObjectId("_id").toString(),
                name = document.getString("name"),
                userName = document.getString("username"),
                password = document.getString("password"),
                createAt = document.getDate("createdAt")
            )

        }
    }

    // UserModel을 Document으로 변환 시키는 함수
    fun toDocument(): Document {
        return Document(
            mapOf(
                "name" to name,
                "username" to userName,
                "password" to password,
                "createdAt" to createAt
            )
        )

    }
}