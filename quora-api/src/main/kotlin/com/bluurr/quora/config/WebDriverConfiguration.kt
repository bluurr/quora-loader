package com.bluurr.quora.config

import io.github.bonigarcia.wdm.WebDriverManager
import io.github.bonigarcia.wdm.config.DriverManagerType.CHROME
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

@Configuration
class WebDriverConfiguration {


    @PostConstruct
    fun setupDriver() {
        WebDriverManager.getInstance(CHROME).setup();
    }

}
