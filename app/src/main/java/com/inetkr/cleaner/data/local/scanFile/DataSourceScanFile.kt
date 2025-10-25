package com.inetkr.cleaner.data.local.scanFile

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.entity.MediaFile
import com.inetkr.cleaner.domain.entity.MediaType
import dagger.hilt.android.qualifiers.ApplicationContext
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
                val size = cursor.getInt(sizeColumn)

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
            MediaStore.Images.Media.SIZE
        )

        val query = context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DISPLAY_NAME} ASC"
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)

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
                println("Image: $name, Size: $size bytes, thumbnailUri: ${mediaFile.thumbnailUri}")
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

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun getAllFolder() {
        val imageUris = mutableListOf<Uri>()
        val collection = MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        )
        val projection = arrayOf(MediaStore.Images.Media._ID)
        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val contentUri = Uri.withAppendedPath(collection, id.toString())
                imageUris.add(contentUri)
            }
        }
        println(imageUris)
    }
}