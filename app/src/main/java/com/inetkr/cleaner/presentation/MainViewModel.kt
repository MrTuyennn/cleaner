package com.inetkr.cleaner.presentation

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.usecase.LoadAppConfigUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appConfigUseCase: LoadAppConfigUseCase
): BaseViewModel() {

    fun getAppConfig() {
        viewModelScope.launch {
            appConfigUseCase.invoke()
        }
    }
}