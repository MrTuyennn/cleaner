package com.inetkr.cleaner.domain.usecase

import com.inetkr.cleaner.data.AppConfigRepository
import javax.inject.Inject

class LoadAppConfigUseCase @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) {
    suspend operator fun invoke() = appConfigRepository.getAppConfig()
}

class GetAppConfigUseCase @Inject constructor(
    private val appConfigRepository: AppConfigRepository
) {
    operator fun invoke() = appConfigRepository.appConfigInfo
}