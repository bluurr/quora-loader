package com.bluurr.quora.config

import com.bluurr.quora.domain.user.LoginCredential
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuoraClientConfiguration {

    @Bean
    fun loginCredential(@Value("\${quora.login.username}") username: String, @Value("\${quora.login.password}") password: String) : LoginCredential {

        return LoginCredential.builder()
            .username(username)
            .password(password)
            .build()
    }
}
