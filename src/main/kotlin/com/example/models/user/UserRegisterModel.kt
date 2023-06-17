package com.example.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterModel(val name: String, val userName: String, val password: String)
