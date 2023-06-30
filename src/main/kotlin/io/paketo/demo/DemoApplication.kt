package io.paketo.demo

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
	info = Info(
		title = "EPERS-Online API",
		version = "0.0.1",
		description = "Future GOTY from Spicy Games"
	)
)
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}
