package com.bluurr.quora

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuoraApiApplication

fun main(args: Array<String>) {
    runApplication<QuoraApiApplication>(*args)
}
