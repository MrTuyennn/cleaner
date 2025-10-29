package com.inetkr.cleaner.data.local.scanner

import android.content.Context
import android.os.Environment
import android.util.Log
import com.inetkr.cleaner.utils.helper.toHumanReadableFileSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

data class JunkItem(
    val path: String,
    val size: Long,
    val type: JunkType
)

enum class JunkType {
    CACHE,
    TEMP,
    CORPSE,
    EMPTY_FILE,
    EMPTY_FOLDER
}


class DataSourceScanner @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val packageManager = context.packageManager

    suspend fun scanJunk(): List<JunkItem> = withContext(Dispatchers.IO) {
        val result = mutableListOf<JunkItem>()

        val rootFile = Environment.getExternalStorageDirectory()
        val lsFile = rootFile.listFiles()
        for (file in lsFile){
            if(file.isDirectory && file.name.startsWith("Android")){
                for (f in file.listFiles()) {
                    if(f.isDirectory){
                        Log.e("Cleaner ------>@@", "CACHE => ${f}\n")
                       for (s in f.listFiles()){
                           Log.d("Cleaner ------>@@", "CACHE => ${s}\n")
                       }
                    }

                }
            }
        }

        // 1) App cache: Android/data/*/cache
        scanAppCache(result)

        // 2) Temp: file .tmp
        scanTempFiles(result)

        // 3) Corpse: thư mục app đã gỡ
        scanCorpseFiles(result)

        // 4) Empty file & 5) Empty folder
        scanEmpty(result)

        println("total size: ${result.size}")

        result
    }

    // ========== SCAN HELPERS ======= //

    private fun scanAppCache(out: MutableList<JunkItem>) {
        val dir = File("/storage/emulated/0/Android/data/")
        dir.listFiles()?.forEach { appDir ->
            val cache = File(appDir, "cache")
            if (cache.exists()) {
                cache.walk().forEach { f ->
                    if (f.isFile) {
                        out.add(JunkItem(f.path, f.length(), JunkType.CACHE))
                        Log.d("Cleaner", "CACHE => ${f.path}")
                    }
                }
            }
        }
    }

    private fun scanTempFiles(out: MutableList<JunkItem>) {
        val root = File("/storage/emulated/0/")
        root.walk().forEach { f ->
            if (f.isFile && f.name.endsWith(".tmp")) {
                out.add(JunkItem(f.path, f.length(), JunkType.TEMP))
                Log.d("Cleaner", "TEMP => ${f.path}")
            }
        }
    }

    private fun scanCorpseFiles(out: MutableList<JunkItem>) {
        val installedPkgs = packageManager.getInstalledPackages(0).map { it.packageName }
        val dataRoot = File("/storage/emulated/0/Android/data/")

        dataRoot.listFiles()?.forEach { folder ->
            if (!installedPkgs.contains(folder.name)) {
                out.add(JunkItem(folder.path, folder.length(), JunkType.CORPSE))
                Log.d("Cleaner", "CORPSE => ${folder.path}")
            }
        }
    }

    private fun scanEmpty(out: MutableList<JunkItem>) {
        val root = File("/storage/emulated/0/")
        root.walk().forEach { f ->
            if (f.isFile && f.length() == 0L) {
                out.add(JunkItem(f.path, 0, JunkType.EMPTY_FILE))
                Log.d("Cleaner", "EMPTY_FILE => ${f.path}")
            } else if (f.isDirectory && f.listFiles().isNullOrEmpty()) {
                out.add(JunkItem(f.path, 0, JunkType.EMPTY_FOLDER))
                Log.d("Cleaner", "EMPTY_FOLDER => ${f.path}")
            }
        }
    }
}
