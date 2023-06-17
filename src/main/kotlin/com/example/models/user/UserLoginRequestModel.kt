package com.example.models.user

import kotlinx.serialization.Serializable


@Serializable
data class UserLoginRequestModel(val userName: String, val password: String)
