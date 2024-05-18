package com.example.tipjar.domain.usecase

import com.example.tipjar.data.repository.TipRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val tipRepository: TipRepository
) {
    suspend operator fun invoke(timestamp: Long): Boolean {
        return tipRepository.delete(timestamp)
    }
}