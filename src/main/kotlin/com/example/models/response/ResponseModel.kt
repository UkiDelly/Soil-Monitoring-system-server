package com.example.models.response

import kotlinx.serialization.Serializable


@Serializable
data class ResponseModel<T>(val data: T?, val message: String)