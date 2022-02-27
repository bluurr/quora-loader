package com.bluurr.quora.config

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfiguration {

    @Bean
    fun questionCache(cacheManager: CacheManager)  = cacheManager.getCache("questions") ?: throw RuntimeException("Cache not found")
}
