package com.ougi.callme.di

import com.ougi.callme.data.repository.KeyRepositoryImpl
import com.ougi.callme.domain.repository.KeyRepository
import com.ougi.callme.domain.usecase.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private val dataModule = module {
    singleOf(::KeyRepositoryImpl) { bind<KeyRepository>() }
}

private val domainModule = module {
    singleOf(::GenerateTokenUseCaseImpl) { bind<GenerateTokenUseCase>() }
    singleOf(::VerifyTokenUseCaseImpl) { bind<VerifyTokenUseCase>() }
    singleOf(::RefreshTokenUseCaseImpl) { bind<RefreshTokenUseCase>() }
}

val appModule = module {
    includes(
        dataModule,
        domainModule,
    )
}