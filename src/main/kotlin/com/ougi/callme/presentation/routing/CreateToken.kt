package com.ougi.callme.presentation.routing

import com.ougi.callme.domain.usecase.GenerateTokenUseCase
import com.ougi.callme.presentation.model.CreateTokenRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.createToken() {
    val generateTokenUseCase by inject<GenerateTokenUseCase>()
    post("/create") {
        val claim = call.receive<CreateTokenRequest>().claim
        val token = generateTokenUseCase.generateToken(claim)
        call.respond(
            status = HttpStatusCode.OK,
            message = token
        )
    }
}