package io.paketo.demo.webservice

import io.paketo.demo.webservice.mapper.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@CrossOrigin
@RequestMapping("/")
@RestController
class MainController {

    @Operation(summary = "This is a default endpoint to welcome new players")
    @ApiResponse(
        responseCode = "200",
        description = "Welcome to EPERS-Online",
        content = [
            Content(
                mediaType = "application/json",
                schema = Schema(implementation = String::class),
                examples = [
                    ExampleObject(
                        value = "Welcome to EPERS Online"
                    )
                ]
            )
        ]
    )
    @GetMapping("/")
    fun default() : ResponseEntity<Any> {
        return ResponseEntity.status(200).body("Welcome to EPERS Online")
    }

}