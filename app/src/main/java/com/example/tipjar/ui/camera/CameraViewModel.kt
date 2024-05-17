package com.example.tipjar.ui.camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.tipjar.BaseViewModel
import com.example.tipjar.domain.usecase.UpdateReceiptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateReceipt: UpdateReceiptUseCase
) :
    BaseViewModel<CameraContract.State, CameraContract.Event, CameraContract.SideEffect>(
        CameraContract.State(
            timestamp = savedStateHandle["timestamp"] ?: 0L
        )
    ) {


    override fun handleEvents(event: CameraContract.Event) {
        when (event) {
            is CameraContract.Event.OnPhotoTaken -> {
                if (state.value.timestamp == 0L) {
                    throw Exception("Something went wrong. Could not capture image. Image name not set.")
                }
                setState { copy(bitmap = event.bitmap) }
            }

            CameraContract.Event.OnDiscardClick -> setState { copy(bitmap = null) }
            is CameraContract.Event.SaveReceipt -> saveReceipt(event.receiptImagePath)
        }
    }

    private fun saveReceipt(receiptImagePath: String) = viewModelScope.launch {

        if (state.value.timestamp == 0L) {
            throw Exception("Something went wrong. Could not save receipt. Identifier timestamp not set.")
        }
        if (updateReceipt(state.value.timestamp, receiptImagePath)) {
            setSideEffect(CameraContract.SideEffect.ReceiptSaveSuccess)
            setState { CameraContract.State() }
        } else {
            setSideEffect(CameraContract.SideEffect.ReceiptSaveFailed)
        }
    }
}