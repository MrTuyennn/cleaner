package com.inetkr.cleaner.presentation.storage

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.usecase.FolderSystemUseCase
import com.inetkr.cleaner.domain.usecase.GetFolderSystemUseCase
import com.inetkr.cleaner.domain.usecase.SaveFolderSystemUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFolderSystemUseCase: GetFolderSystemUseCase,
    private val folderSystemUseCase: FolderSystemUseCase,
    private val saveFolderSystemUseCase : SaveFolderSystemUseCase,
    ) : BaseViewModel() {
    val folderSystem = folderSystemUseCase.invoke()

    init {
        viewModelScope.launch {
            getFolderSystemUseCase.invoke()
        }
    }

    fun saveFolderSystem(folder: Folder) {
        viewModelScope.launch {
            saveFolderSystemUseCase.invoke(folder)
        }
    }
}