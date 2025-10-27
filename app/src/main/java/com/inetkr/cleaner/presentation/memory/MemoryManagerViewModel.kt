package com.inetkr.cleaner.presentation.memory

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.entity.MemoryManager
import com.inetkr.cleaner.domain.entity.StorageManagers
import com.inetkr.cleaner.domain.usecase.MemoryManagerUseCase
import com.inetkr.cleaner.domain.usecase.StorageManagerUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemoryManagerViewModel @Inject constructor(
    private val memoryManagerUseCase: MemoryManagerUseCase,
    private val storageManagerUseCase: StorageManagerUseCase
) : BaseViewModel() {

    val _memory = MutableStateFlow<MemoryManager?>(null)
    val _storage = MutableStateFlow<StorageManagers?>(null)
    val memory = _memory.asStateFlow()
    val storage = _storage.asStateFlow()

    init {
        viewModelScope.launch {
           val resultStorage = storageManagerUseCase.invoke()
            val resultMemory = memoryManagerUseCase.invoke()
            resultMemory.fold(
                ifLeft = { error ->
                    _memory.value = MemoryManager(
                        totalMemory = 0,
                        availableMemory = 0,
                        usedMemory = 0
                    )
                },
                ifRight = { result ->
                    _memory.value = result
                }
            )

            resultStorage.fold(
                ifLeft = { error ->
                    _storage.value = StorageManagers(
                        totalBytes = 0,
                        usedBytes = 0,
                        freeBytes = 0
                    )
                },
                ifRight = { result ->
                    _storage.value = result
                }
            )
        }
    }
}