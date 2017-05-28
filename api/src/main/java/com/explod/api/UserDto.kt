package com.explod.api

import com.google.gson.annotations.SerializedName
import java.util.*


data class CreateUser(
        val username: String,
        val email: String,
        @SerializedName("full_name") val fullName: String,
        val password: String
)

data class UserCreated(
        val username: String,
        val email: String,
        val created: Date
)

data class Exists(
        val exists: Boolean
        //val key: String,
        //val value: String
)

data class Profile(
        val username: String,
        val email: String,
        @SerializedName("full_name") val fullName: String,
        val created: Date
)

data class Login(
        val username: String,
        val password: String,
        val source: String,
        val sourceName: String,
        @SerializedName("source_version") val sourceVersion: String
)

data class Token(
        val expiration: Date,
        val jwt: String,
        val source: String,
        val sourceName: String,
        @SerializedName("source_version") val sourceVersion: String
)