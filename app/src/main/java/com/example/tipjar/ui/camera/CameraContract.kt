package com.example.tipjar.ui.camera

import android.graphics.Bitmap
import com.example.tipjar.UiEvent
import com.example.tipjar.UiSideEffect
import com.example.tipjar.UiState

class CameraContract {
    data class State(
        val timestamp: Long = 0L,
        val bitmap: Bitmap? = null
    ) : UiState

    sealed class Event : UiEvent {
        data class OnPhotoTaken(val bitmap: Bitmap) : Event()
        data object OnDiscardClick : Event()
        data class SaveReceipt(val receiptImagePath: String) : Event()
    }

    sealed class SideEffect : UiSideEffect {
        data object ReceiptSaveSuccess : SideEffect()
        data object ReceiptSaveFailed : SideEffect()
    }
}