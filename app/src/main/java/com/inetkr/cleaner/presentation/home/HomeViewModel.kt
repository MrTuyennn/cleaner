package com.inetkr.cleaner.presentation.home

import com.inetkr.cleaner.domain.usecase.GetAppConfigUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appConfigUseCase: GetAppConfigUseCase
): BaseViewModel() {
    val appConfig = appConfigUseCase.invoke()
}