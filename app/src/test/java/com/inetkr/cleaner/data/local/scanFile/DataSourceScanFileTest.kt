package com.inetkr.cleaner.data.local.scanFile

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.fail
import com.inetkr.cleaner.domain.entity.MediaFile
import com.inetkr.cleaner.domain.entity.MediaType
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class DataSourceScanFileTest {

    private lateinit var mockContext: Context
    private lateinit var mockContentResolver: ContentResolver
    private lateinit var dataSource: DataSourceScanFile

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockContext = mockk()
        mockContentResolver = mockk()
        every { mockContext.contentResolver } returns mockContentResolver
        dataSource = DataSourceScanFile(mockContext)
    }

    @Test
    fun `deleteItem should return Right true when delete successful`() = runTest {
        val mockUri = mockk<Uri>()
        // Given
        val mediaFile = MediaFile(
            id = 1L,
            uri = mockUri,
            name = "test.jpg",
            duration = 10,
            size = 1024,
            type = MediaType.IMAGE,
            thumbnailUri = "content://test/thumbnail.jpg"
        )

        every { mockContentResolver.delete(any(), any(), any()) } returns 1

        val result = dataSource.deleteItem(mediaFile)

        assertThat(result.isRight()).isTrue()

        result.fold(
            ifLeft = { fail("Should not return error") },
            ifRight = { assertThat(it).isTrue() }
        )
    }
}