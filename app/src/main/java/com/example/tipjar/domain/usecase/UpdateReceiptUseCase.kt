package com.example.tipjar.domain.usecase

import com.example.tipjar.data.repository.TipRepository
import javax.inject.Inject

class UpdateReceiptUseCase @Inject constructor(
    private val tipRepository: TipRepository
) {
    suspend operator fun invoke(timestamp: Long, receiptUri: String): Boolean {
        return tipRepository.updateReceipt(timestamp, receiptUri)
    }
}