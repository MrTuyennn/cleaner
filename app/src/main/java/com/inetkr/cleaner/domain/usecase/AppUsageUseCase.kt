package com.inetkr.cleaner.domain.usecase

import arrow.core.Either
import com.inetkr.cleaner.data.repository.appusage.AppUsageRepository
import com.inetkr.cleaner.domain.entity.AppUsageInfo
import javax.inject.Inject

class GetAppUsageUseCase @Inject constructor(
    private val appUsageRepository: AppUsageRepository
) {
    suspend operator fun  invoke(): Either<Throwable, List<AppUsageInfo>> = appUsageRepository.getAppUsage()
}

class UnInstallAppUseCase @Inject constructor(
    private val appUsageRepository: AppUsageRepository
) {
    suspend operator fun  invoke(appUsage: AppUsageInfo): Either<Throwable, Boolean> = appUsageRepository.unInstallApp(appUsage)
}