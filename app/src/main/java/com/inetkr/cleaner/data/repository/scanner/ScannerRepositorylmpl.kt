package com.inetkr.cleaner.data.repository.scanner

import com.inetkr.cleaner.data.local.scanner.DataSourceScanner
import javax.inject.Inject

class ScannerRepositoryImpl @Inject constructor(
    private val dataSourceScanner: DataSourceScanner
) : ScannerRepository {
    override suspend fun scanner() {
        dataSourceScanner.scanJunk()
    }
}