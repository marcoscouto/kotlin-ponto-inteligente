package com.marcoscouto.pontointeligente.pontointeligente

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class PontointeligenteApplication

fun main(args: Array<String>) {
    runApplication<PontointeligenteApplication>(*args)
}
