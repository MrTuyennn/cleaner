package com.inetkr.cleaner.domain.usecase

import com.inetkr.cleaner.data.repository.scanner.ScannerRepository
import javax.inject.Inject


class ScannerUseCase @Inject constructor(
    private val scannerRepository: ScannerRepository
) {
    suspend operator fun invoke() {
        scannerRepository.scanner()
    }
}