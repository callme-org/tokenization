package com.ougi.callme.domain.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ougi.callme.domain.repository.JwtInfo
import com.ougi.callme.domain.repository.KeyRepository
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

interface VerifyTokenUseCase {

    fun verifyAccessToken(token: String): Boolean

    fun verifyRefreshToken(token: String): Boolean
}

class VerifyTokenUseCaseImpl(
    private val keyRepository: KeyRepository,
) : VerifyTokenUseCase {

    override fun verifyAccessToken(token: String) =
        verify(
            keyPair = keyRepository.accessKeyPair,
            token = token
        )


    override fun verifyRefreshToken(token: String) =
        verify(
            keyPair = keyRepository.refreshKeyPair,
            token = token
        )

    private fun verify(
        keyPair: KeyPair,
        token: String,
    ) =
        runCatching {
            createVerifier(keyPair).verify(token)
            true
        }.getOrDefault(false)

    private fun createVerifier(keyPair: KeyPair) =
        JWT.require(
            Algorithm.RSA256(
                keyPair.public as RSAPublicKey,
                keyPair.private as RSAPrivateKey
            )
        )
            .withAudience(JwtInfo.audience)
            .withIssuer(JwtInfo.issuer)
            .build()

}