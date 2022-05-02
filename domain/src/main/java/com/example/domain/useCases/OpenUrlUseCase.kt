package com.example.domain.useCases

import com.example.data.repositories.PlatformRepository
import javax.inject.Inject

class OpenUrlUseCase @Inject constructor(private val platformRepository: PlatformRepository) {
    operator fun invoke(url: String) = platformRepository.openUrl(url)
}