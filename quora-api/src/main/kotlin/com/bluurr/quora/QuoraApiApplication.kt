package com.bluurr.quora

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class QuoraApiApplication

fun main(args: Array<String>) {
    runApplication<QuoraApiApplication>(*args)
}
