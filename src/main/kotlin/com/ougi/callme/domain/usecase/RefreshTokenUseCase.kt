package com.ougi.callme.domain.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ougi.callme.domain.model.Claim
import com.ougi.callme.domain.model.TokenPair
import com.ougi.callme.domain.repository.JwtInfo
import com.ougi.callme.domain.repository.KeyRepository
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

interface RefreshTokenUseCase {

    fun refreshToken(
        token: String,
        claimParamName: String,
    ): TokenPair

}

class RefreshTokenUseCaseImpl(
    private val generateTokenUseCase: GenerateTokenUseCase,
    private val verifyTokenUseCase: VerifyTokenUseCase,
    private val keyRepository: KeyRepository,
) : RefreshTokenUseCase {

    private val verifier by lazy {
        JWT.require(
            Algorithm.RSA256(
                keyRepository.refreshKeyPair.public as RSAPublicKey,
                keyRepository.refreshKeyPair.private as RSAPrivateKey
            )
        )
            .withAudience(JwtInfo.audience)
            .withIssuer(JwtInfo.issuer)
            .build()
    }

    override fun refreshToken(
        token: String,
        claimParamName: String,
    ): TokenPair =
        when (verifyTokenUseCase.verifyRefreshToken(token)) {
            true -> generateTokenUseCase.generateToken(
                Claim(
                    name = claimParamName,
                    value = verifier.verify(token)
                        .getClaim(claimParamName)
                        .asString()
                )
            )

            false -> throw Exception("Not authorized")
        }


}