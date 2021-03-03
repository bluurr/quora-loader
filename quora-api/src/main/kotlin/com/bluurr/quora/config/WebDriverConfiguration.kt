package com.bluurr.quora.config

import com.bluurr.quora.client.BaseQuoraClient
import com.bluurr.quora.client.QuoraClient
import com.bluurr.quora.extension.EnhancedDriver
import com.bluurr.quora.model.user.LoginCredential
import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType.CHROME
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

        private val log: Logger = LoggerFactory.getLogger(this.javaClass)

        fun createDriver(): EnhancedDriver {

            return EnhancedDriver(driverProperties.baseUrl, chromeDriver())
        }

        private fun chromeDriver() : ChromeDriver {

            val options = chromeOptions()

            return ChromeDriver(options)
        }

        private fun chromeOptions() : ChromeOptions {

            val options = ChromeOptions()

            options.setHeadless(driverProperties.headless)

            if (driverProperties.binary.isNotBlank()) {

                log.info("Overwriting binary location: ${driverProperties.binary}")

                options.setBinary(driverProperties.binary)
            }

            // Quora TOS require the user agent to include a contact email.
            options.addArguments("--user-agent=quora_loader;${driverProperties.contactEmail};")

            return options
        }
    }
}

@ConstructorBinding
@ConfigurationProperties("web.driver")
data class WebDriverProperties(val baseUrl: String, val headless: Boolean, val contactEmail: String, val binary: String)
