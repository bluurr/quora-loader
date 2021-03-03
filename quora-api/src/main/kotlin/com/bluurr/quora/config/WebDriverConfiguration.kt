package com.bluurr.quora.config

import com.bluurr.quora.client.BaseQuoraClient
import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.extension.EnhancedDriver
import com.bluurr.quora.model.user.LoginCredential
import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType.CHROME
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Configuration
class WebDriverConfiguration {

    @Bean
    fun quoraClients(loginCredential: LoginCredential, driverFactory: DriverFactory) : List<QuoraClient> {

        // Preload 3 quora client instances.
       return (1..3).map {

           BaseQuoraClient(loginCredential, driverFactory)
        }
       .toList()
    }

    @PostConstruct
    fun setupDriver() {

        WebDriverManager.getInstance(CHROME).setup()
    }

    @Component
    class DriverFactory(val driverProperties: WebDriverProperties) {

        fun createDriver(): EnhancedDriver {

            return EnhancedDriver(driverProperties.baseUrl, chromeDriver())
        }

        private fun chromeDriver() : ChromeDriver {

            val options = chromeOptions()

            return ChromeDriver(options)
        }

        private fun chromeOptions() : ChromeOptions {

            val options = ChromeOptions()

            if (driverProperties.headless) {
                options.addArguments("--headless")
            }
            return options
        }
    }
}

@ConstructorBinding
@ConfigurationProperties("web.driver")
data class WebDriverProperties(val baseUrl: String, val headless: Boolean)
