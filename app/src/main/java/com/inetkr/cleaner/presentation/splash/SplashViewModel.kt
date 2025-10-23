package com.inetkr.cleaner.presentation.splash

import androidx.lifecycle.viewModelScope
import com.inetkr.cleaner.domain.usecase.GetAppConfigUseCase
import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SplashEvent {
    object NavigateToHome : SplashEvent
}


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val appConfigUseCase: GetAppConfigUseCase
): BaseViewModel() {
    private val _events = MutableSharedFlow<SplashEvent>(replay = 0)
    val events = _events.asSharedFlow()

    val appInfo = appConfigUseCase.invoke()

    init {
        viewModelScope.launch {
            delay(3000)
            _events.emit(SplashEvent.NavigateToHome)
        }
    }

}