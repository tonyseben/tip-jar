package com.example.tipjar.domain.usecase

import com.example.tipjar.data.repository.TipRepository
import com.example.tipjar.domain.mapper.toTipData
import com.example.tipjar.domain.model.TipData
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val tipRepository: TipRepository
) {
    suspend operator fun invoke(tip: TipData): List<TipData> {
        return tipRepository.getAll().map { it.toTipData() }
    }
}