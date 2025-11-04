package com.inetkr.cleaner.presentation.storagedetail

import com.inetkr.cleaner.domain.usecase.CurrentFolderSystemUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageDetailViewModel @Inject constructor(
    private val currentFolderSystemUseCase: CurrentFolderSystemUseCase
): BaseViewModel() {

    val currentFolderSystem = currentFolderSystemUseCase.invoke()

    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    fun showBottomSheet() {
        _showBottomSheet.value = true
    }

    fun hideBottomSheet() {
        _showBottomSheet.value = false
    }
}