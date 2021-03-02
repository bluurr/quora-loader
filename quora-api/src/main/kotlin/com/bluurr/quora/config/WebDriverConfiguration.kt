package com.bluurr.quora.config

import com.bluurr.quora.extension.EnhancedDriver
import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType.CHROME
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Configuration
class WebDriverConfiguration {

    @PostConstruct
    fun setupDriver() {

        WebDriverManager.getInstance(CHROME).setup();
    }

    @Component
    class DriverFactory {

        companion object {
            private const val quoraUrl = "https://www.quora.com/"
        }

        fun createDriver(): EnhancedDriver {

            return EnhancedDriver(quoraUrl, ChromeDriver())
        }
    }

}
