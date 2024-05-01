package com.ougi.callme.presentation.model

import com.ougi.callme.domain.model.Claim
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreateTokenRequest(
    @SerialName("claim")
    val claim: Claim,
)