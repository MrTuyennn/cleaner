package com.inetkr.cleaner.data.repository.scanFile

import android.net.Uri
import arrow.core.Either
import com.google.common.truth.Truth.assertThat
import com.inetkr.cleaner.data.local.scanFile.DataSourceScanFile
import com.inetkr.cleaner.data.repository.scanFile.ScanFileRepositoryImpl
import com.inetkr.cleaner.domain.entity.MediaFile
import com.inetkr.cleaner.domain.entity.MediaType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestScope
import org.bouncycastle.util.test.SimpleTest.runTest
import org.junit.Assert.fail

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ScanFileRepositoryImplTest {

    private lateinit var mockDataSource: DataSourceScanFile
    private lateinit var repository: ScanFileRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockDataSource = mockk()
        repository = ScanFileRepositoryImpl(Dispatchers.Unconfined, mockDataSource)
    }

    @Test
    fun `scanVideoFile should return videos from dataSource`() = runTest {
        val mockUri = mockk<Uri>()
        // Given
        val expectedVideos = listOf(
            MediaFile(
                id = 1L,
                uri = Uri.parse("content://test/video1.mp4"),
                name = "video1.mp4",
                duration = 100,
                size = 1024,
                type = MediaType.VIDEO,
                thumbnailUri = "content://test/thumb1.jpg"
            )
        )

        coEvery { mockDataSource.scanAllVideos() } returns expectedVideos

        // When
        val result = repository.scanVideoFile()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("video1.mp4")
    }


    @Test
    fun `scanImageFile should return images from dataSource`() = runTest {
        val mockUri = mockk<Uri>()
        val mocThumbnails = mockk<String>()
        // Given
        val expectedImages = listOf(
            MediaFile(
                id = 2L,
                uri = mockUri,
                name = "image1.jpg",
                duration = null,
                size = 512,
                type = MediaType.IMAGE,
                thumbnailUri = mocThumbnails
            )
        )

        coEvery { mockDataSource.scanAllImages() } returns expectedImages

        // When
        val result = repository.scanImageFile()

        // Then
        assertThat(result).hasSize(1)
        assertThat(result[0].name).isEqualTo("image1.jpg")
    }

    @Test
    fun `deleteFileItem should return success when dataSource delete succeeds`() = runTest {
        // Given
        val mediaFile = MediaFile(
            id = 1L,
            uri = Uri.parse("content://test/test.jpg"),
            name = "test.jpg",
            duration = null,
            size = 1024,
            type = MediaType.IMAGE,
            thumbnailUri = "content://test/thumb.jpg"
        )

        coEvery { mockDataSource.deleteItem(mediaFile) } returns Either.Right(true)

        // When
        val result = repository.deleteFileItem(mediaFile)

        // Then
        assertThat(result.isRight()).isTrue()
        result.fold(
            ifLeft = { fail("Should not return error") },
            ifRight = { success -> assertThat(success).isTrue() }
        )
    }

    @Test
    fun `deleteFileItem should return error when dataSource delete fails`() = runTest {
        // Given
        val mediaFile = MediaFile(
            id = 1L,
            uri = Uri.parse("content://test/test.jpg"),
            name = "test.jpg",
            duration = null,
            size = 1024,
            type = MediaType.IMAGE,
            thumbnailUri = "content://test/thumb.jpg"
        )

        val expectedError = RuntimeException("Delete failed")
        coEvery { mockDataSource.deleteItem(mediaFile) } returns Either.Left(expectedError)

        // When
        val result = repository.deleteFileItem(mediaFile)

        // Then
        assertThat(result.isLeft()).isTrue()
        result.fold(
            ifLeft = { error -> assertThat(error).isEqualTo(expectedError) },
            ifRight = { fail("Should return error") }
        )
    }
}