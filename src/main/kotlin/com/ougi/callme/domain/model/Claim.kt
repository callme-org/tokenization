package com.ougi.callme.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Claim(
    @SerialName("name")
    val name: String,
    @SerialName("value")
    val value: String,
)