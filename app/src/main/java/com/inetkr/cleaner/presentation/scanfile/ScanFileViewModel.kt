package com.inetkr.cleaner.presentation.scanfile

import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.inetkr.cleaner.domain.entity.MediaFile
import com.inetkr.cleaner.domain.usecase.DeleteFileItemUseCase
import com.inetkr.cleaner.domain.usecase.ScanFileImageUseCase
import com.inetkr.cleaner.domain.usecase.ScanFileVideoUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanFileViewModel @Inject constructor(
    private val scanFileVideoUseCase: ScanFileVideoUseCase,
    private val scanFileImageUseCase: ScanFileImageUseCase,
    private val deleteFileItemUseCase: DeleteFileItemUseCase
): BaseViewModel() {

    private val _scanState = MutableStateFlow<ScanFileState>(ScanFileState.Idle)
    val scanState = _scanState.asStateFlow()

    init {
        scanFiles()
    }

    private fun scanFiles() {
        viewModelScope.launch {
            _scanState.value = ScanFileState.Loading

            val imageResult = scanFileImageUseCase.invoke()
            val videoResult = scanFileVideoUseCase.invoke()

            when {
                imageResult is Either.Left && videoResult is Either.Left -> {
                    _scanState.value = ScanFileState.Error("Failed to scan files")
                }
                else -> {
                    val images = (imageResult as? Either.Right)?.value ?: emptyList()
                    val videos = (videoResult as? Either.Right)?.value ?: emptyList()

                    _scanState.value = ScanFileState.Success(
                        images = images,
                        videos = videos
                    )
                }
            }
        }
    }

    fun deleteItemFile(mediaFile: MediaFile) {
        viewModelScope.launch {
          val result = deleteFileItemUseCase.invoke(mediaFile)
            result.fold(
                ifLeft = { error ->
                    _scanState.value = ScanFileState.Error("Delete failed: ${error.message}")
                },
                ifRight = { success ->
                    removeItemFromState(mediaFile)
                },
            )
        }
    }

    private fun removeItemFromState(mediaFile: MediaFile) {
        val currentState = _scanState.value
        if (currentState is ScanFileState.Success) {
            val updatedImages = currentState.images.filter { it.uri != mediaFile.uri }
            val updatedVideos = currentState.videos.filter { it.uri != mediaFile.uri }

            _scanState.value = ScanFileState.Success(
                images = updatedImages,
                videos = updatedVideos
            )
        }
    }
}