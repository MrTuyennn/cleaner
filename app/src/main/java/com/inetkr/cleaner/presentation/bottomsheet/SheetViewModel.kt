package com.inetkr.cleaner.presentation.bottomsheet

import com.inetkr.cleaner.utils.appcomponent.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class BottomSheetEvent {
    object Show : BottomSheetEvent()
    object Hide : BottomSheetEvent()
    data class ShowWithData(val value: String) : BottomSheetEvent()
}

class SheetViewModel : BaseViewModel() {
    private val _events = MutableSharedFlow<BottomSheetEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    fun onOpenSheet() {
        _events.tryEmit(BottomSheetEvent.Show)
    }

    fun onOpenSheetWithData(value: String) {
        _events.tryEmit(BottomSheetEvent.ShowWithData(value))
    }

    fun onCloseSheet() {
        _events.tryEmit(BottomSheetEvent.Hide)
    }

}