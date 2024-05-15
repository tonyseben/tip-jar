package com.example.tipjar.domain.usecase

import com.example.tipjar.data.repository.TipRepository
import com.example.tipjar.domain.mapper.toTipHistory
import com.example.tipjar.domain.model.TipData
import javax.inject.Inject

class UpdateReceiptUseCase @Inject constructor(
    private val tipRepository: TipRepository
) {
    suspend operator fun invoke(tip: TipData) {
        tipRepository.updateReceipt(tip.toTipHistory())
    }
}