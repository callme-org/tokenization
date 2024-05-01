package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.usecase.RefreshTokenUseCase
import com.ougi.callme.presentation.model.RefreshTokenRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.refreshToken() {
    val refreshTokenUseCase by inject<RefreshTokenUseCase>()
    route("/refresh") {
        handle {
            val request = call.receive<RefreshTokenRequest>()
            val newToken = refreshTokenUseCase.refreshToken(
                token = request.refreshToken,
                claim = request.claim
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = newToken
            )
        }
    }
}