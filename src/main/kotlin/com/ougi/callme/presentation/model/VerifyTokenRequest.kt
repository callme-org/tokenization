package com.ougi.callme.presentation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VerifyTokenRequest(
    @SerialName("accessToken")
    val accessToken: String
)