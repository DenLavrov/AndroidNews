package com.example.domain.useCases

import com.example.data.repositories.NewsRepository
import javax.inject.Inject

class UpdateQueryUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(query: String) = newsRepository.updateQuery(query)
}