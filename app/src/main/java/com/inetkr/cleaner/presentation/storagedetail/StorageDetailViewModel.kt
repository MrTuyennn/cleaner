package com.inetkr.cleaner.presentation.storagedetail

import com.inetkr.cleaner.domain.entity.Folder
import com.inetkr.cleaner.domain.usecase.CurrentFolderSystemUseCase
import com.inetkr.cleaner.domain.usecase.SaveFolderSystemUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StorageDetailViewModel @Inject constructor(
    private val currentFolderSystemUseCase: CurrentFolderSystemUseCase
): BaseViewModel() {

    val currentFolderSystem = currentFolderSystemUseCase.invoke()

}