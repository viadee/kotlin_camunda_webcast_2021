package de.viadee.camunda.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application {
    fun main(args: Array<String>) {
        runApplication<Application>(*args)
    }
}