package com.ougi.callme.domain.usecase

import com.ougi.callme.domain.model.Claim
import com.ougi.callme.domain.model.TokenPair

interface RefreshTokenUseCase {

    fun refreshToken(
        token: String,
        claim: Claim,
    ): TokenPair

}

class RefreshTokenUseCaseImpl(
    private val generateTokenUseCase: GenerateTokenUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
) : RefreshTokenUseCase {

    override fun refreshToken(
        token: String,
        claim: Claim,
    ): TokenPair =
        when (verifyTokenUseCase.verifyRefreshToken(token)) {
            true -> generateTokenUseCase.generateToken(claim)
            false -> throw Exception("Not authorized")
        }

}