package com.seovalue.m2price

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class M2priceServerApplication

fun main(args: Array<String>) {
    runApplication<M2priceServerApplication>(*args)
}
