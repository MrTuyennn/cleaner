package com.inetkr.cleaner.data.local.memorymanager

import android.app.ActivityManager
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.MemoryManager
import com.inetkr.cleaner.domain.entity.StorageManagers
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.UUID
import javax.inject.Inject


class DataSourceMemoryManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getMemoryInfo(): Either<Throwable, MemoryManager> {
        return try {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val memoryInfo = ActivityManager.MemoryInfo()
            activityManager.getMemoryInfo(memoryInfo)

            val totalMemory = memoryInfo.totalMem
            val availableMemory = memoryInfo.availMem
            val usedMemoryMB = totalMemory - availableMemory

            Either.Right(
                MemoryManager(
                    totalMemory = totalMemory,
                    availableMemory = availableMemory,
                    usedMemory = usedMemoryMB
                )
            )
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    suspend fun getStorageInfo(): Either<Throwable, StorageManagers> {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API Level 26 (Oreo)
                val storageManager =
                    context.getSystemService(Context.STORAGE_SERVICE) as StorageManager
                val storageStatsManager =
                    context.getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
                val storageVolumes = storageManager.storageVolumes

                var totalBytes = 0L
                var freeBytes = 0L

                for (volume in storageVolumes) {
                    try {
                        val uuid =
                            volume.uuid?.let { UUID.fromString(it) } ?: StorageManager.UUID_DEFAULT
                        totalBytes += storageStatsManager.getTotalBytes(uuid)
                        freeBytes += storageStatsManager.getFreeBytes(uuid)
                    } catch (e: Exception) {
                        e.message
                    }
                }

                val usedBytes = totalBytes - freeBytes

                println("Total storage: ${totalBytes / (1024 * 1024 * 1024)} GB")
                println("Used storage: ${usedBytes / (1024 * 1024 * 1024)} GB")
                println("Free storage: ${freeBytes / (1024 * 1024 * 1024)} GB")
                Either.Right(
                    StorageManagers(
                        totalBytes = totalBytes,
                        usedBytes = usedBytes,
                        freeBytes = freeBytes
                    )
                )
            } else {  // Handle devices below API 26
                val stat = StatFs(Environment.getDataDirectory().path)
                val blockSize = stat.blockSizeLong
                val totalBlocks = stat.blockCountLong
                val availableBlocks = stat.availableBlocksLong

                val totalBytes = totalBlocks * blockSize
                val freeBytes = availableBlocks * blockSize
                val usedBytes = totalBytes - freeBytes

                Either.Right(
                    StorageManagers(
                        totalBytes = totalBytes,
                        usedBytes = usedBytes,
                        freeBytes = freeBytes
                    )
                )
            }
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}