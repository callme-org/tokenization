package com.ougi.callme.domain.usecase

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ougi.callme.domain.model.Claim
import com.ougi.callme.domain.model.TokenPair
import com.ougi.callme.domain.repository.JwtInfo
import com.ougi.callme.domain.repository.KeyRepository
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.concurrent.TimeUnit

interface GenerateTokenUseCase {

    fun generateToken(claim: Claim): TokenPair

}

class GenerateTokenUseCaseImpl(
    private val keyRepository: KeyRepository,
) : GenerateTokenUseCase {

    override fun generateToken(claim: Claim): TokenPair =
        TokenPair(
            accessToken =
            createToken(
                claim = claim,
                tokenExpiringMillis = accessTokenExpiringMillis,
                keyPair = keyRepository.accessKeyPair,
            ),
            refreshToken =
            createToken(
                claim = claim,
                tokenExpiringMillis = refreshTokenExpiringMillis,
                keyPair = keyRepository.refreshKeyPair,
            ),
        )

    private fun createToken(
        claim: Claim,
        tokenExpiringMillis: Long,
        keyPair: KeyPair
    ) = JWT.create()
        .withAudience(JwtInfo.audience)
        .withIssuer(JwtInfo.issuer)
        .withClaim(claim.name, claim.value)
        .withExpiresAt(Date(System.currentTimeMillis() + tokenExpiringMillis))
        .sign(
            Algorithm.RSA256(
                keyPair.public as RSAPublicKey,
                keyPair.private as RSAPrivateKey
            )
        )


    private companion object {
        private val accessTokenExpiringMillis = TimeUnit.HOURS.toMillis(1)
        private val refreshTokenExpiringMillis = TimeUnit.DAYS.toMillis(30)
    }
}