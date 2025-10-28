package com.inetkr.cleaner.data.local.scanFile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.system.Os
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile
import com.inetkr.cleaner.domain.entity.MediaType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@SuppressLint("Recycle")
class DataSourceScanFile @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun scanAllVideos(): List<MediaFile> {
        val mediaList = mutableListOf<MediaFile>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
        )

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
        )
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getLong(sizeColumn)

                val mediaFile = MediaFile(
                    id = id,
                    uri = Uri.withAppendedPath(collection, id.toString()),
                    name = name,
                    duration = duration,
                    size = size,
                    type = MediaType.VIDEO,
                    thumbnailUri = Uri.withAppendedPath(collection, id.toString()).toString()
                )

                mediaList.add(mediaFile)
            }
        }

        return mediaList
    }

    suspend fun scanAllImages(): List<MediaFile> {
        val mediaList = mutableListOf<MediaFile>()
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_ADDED
        )

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} ASC"
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getLong(sizeColumn)

                val mediaFile = MediaFile(
                    id = id,
                    uri = Uri.withAppendedPath(collection, id.toString()),
                    name = name,
                    duration = null,
                    size = size,
                    type = MediaType.IMAGE,
                    thumbnailUri = Uri.withAppendedPath(collection, id.toString()).toString()
                )

                mediaList.add(mediaFile)
                //    println("Image: $name, Size: $size bytes, thumbnailUri: ${mediaFile.thumbnailUri}")
            }
        }
        return mediaList
    }

    suspend fun deleteItem(mediaFile: MediaFile): Either<Throwable, Boolean> {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val collection = if (mediaFile.type == MediaType.IMAGE) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                } else {
                    MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
                }

                val result = context.contentResolver.delete(
                    collection,
                    "${MediaStore.MediaColumns._ID} = ?",
                    arrayOf(mediaFile.uri.lastPathSegment ?: "")
                )
                Either.Right(result > 0)
            } else {
                val result = context.contentResolver.delete(
                    mediaFile.uri,
                    null,
                    null
                )
                Either.Right(result > 0)
            }
        } catch (e: Exception) {
            Either.Left(e)
        }
    }

    suspend fun getAllFolders(): Either<Throwable, List<Folder>> {

        val folderSet = mutableSetOf<String>()

        val imageCollection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val imageProjection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        val imageQuery = context.contentResolver.query(
            imageCollection,
            imageProjection,
            null,
            null,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        imageQuery?.use { cursor ->
            val bucketNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val folderName = cursor.getString(bucketNameColumn)
                if (!folderName.isNullOrEmpty()) {
                    folderSet.add(folderName)
                }
            }
        }

        // Lấy thư mục từ videos
        val videoCollection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val videoProjection = arrayOf(
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        )

        val videoQuery = context.contentResolver.query(
            videoCollection,
            videoProjection,
            null,
            null,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        )

        videoQuery?.use { cursor ->
            val bucketNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val folderName = cursor.getString(bucketNameColumn)
                if (!folderName.isNullOrEmpty()) {
                    folderSet.add(folderName)
                }
            }
        }

        return Either.Right(folderSet.map { folderName ->
            Folder(
                name = folderName,
                path = "bucket_$folderName",
                totalSize = 0,
                sTime = 3948239483,
                lsFile = emptyList()
            )
        }.sortedBy { it.name })
    }

    suspend fun getFoldersFromFileSystem(): Either<Throwable, List<Folder>> {
        return try {
            val root = Environment.getExternalStorageDirectory()

            val folderList = root.listFiles()
                ?.filter { it.isDirectory }
                ?.map { file ->
                    val directCount = file.listFiles()?.size ?: 0
                    val sTime = (Os.stat(file.absolutePath).st_ctime * 1000L)

                    Folder(
                        name = file.name,
                        path = file.path,
                        totalSize = directCount,
                        sTime = sTime,
                        lsFile = file?.listFiles()?.toList() ?: emptyList(),
                    )
                }
                ?: emptyList()

            Either.Right(folderList.sortedBy { it.name })
        } catch (e: Exception) {
            Either.Left(e)
        }
    }
}