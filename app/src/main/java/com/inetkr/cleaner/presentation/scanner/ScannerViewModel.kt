package com.inetkr.cleaner.presentation.scanner

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.usecase.ScannerUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val scannerUseCase: ScannerUseCase
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            try {
                scannerUseCase.invoke()
            } catch (e: Exception) {
            }
        }
    }
}