package com.example.domain.useCases

import com.example.data.repositories.NewsRepository
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(query: String) = newsRepository.getNews(50, 10, query)
}