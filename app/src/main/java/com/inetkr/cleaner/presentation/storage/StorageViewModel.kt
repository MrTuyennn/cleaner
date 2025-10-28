package com.inetkr.cleaner.presentation.storage

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.usecase.GetFolderSystemUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getFolderSystemUseCase: GetFolderSystemUseCase
) : BaseViewModel() {

    val _lsFolderSystem = MutableStateFlow<List<Folder>>(emptyList())
    val folderSystem = _lsFolderSystem.asStateFlow()

    init {
        viewModelScope.launch {
            val resultLsFolder = getFolderSystemUseCase.invoke()
            resultLsFolder.fold(
                ifLeft = { error ->
                    println(error)
                },
                ifRight = { lsFolder ->
                    _lsFolderSystem.value = lsFolder
                }
            )
        }
    }
}