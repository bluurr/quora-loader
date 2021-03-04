package com.bluurr.quora.config

import com.bluurr.quora.client.provider.QuoraClientProvider
import com.bluurr.quora.service.QuestionSearchService
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun questionSearchService(clientProvider: QuoraClientProvider, cacheManager: CacheManager) : QuestionSearchService {

        val cache: Cache = cacheManager.getCache("questions") ?: throw RuntimeException("Cache not found")

        return QuestionSearchService(clientProvider, cache)
    }
}
