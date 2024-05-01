package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.usecase.VerifyTokenUseCase
import com.ougi.callme.presentation.model.VerifyTokenRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.verifyToken() {
    val verifyTokenUseCase by inject<VerifyTokenUseCase>()
    route("verify") {
        handle {
            val token = call.receive<VerifyTokenRequest>().accessToken
            when (verifyTokenUseCase.verifyAccessToken(token)) {
                true -> HttpStatusCode.OK to "Token verified"
                false -> HttpStatusCode.Unauthorized to "Token not verified"
            }.let { (status, message) -> call.respond(status, message) }
        }
    }
}