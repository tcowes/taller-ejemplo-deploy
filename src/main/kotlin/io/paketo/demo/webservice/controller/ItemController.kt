package io.paketo.demo.webservice.controller

import io.paketo.demo.model.Item
import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.service.ItemService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@CrossOrigin
@RequestMapping("/inventory")
@RestController
class ItemController {
    @Autowired private lateinit var itemService: ItemService

    @Operation(
        summary = "Adds an Item to the player's inventory",
        description = "Creates an Item in the DB and associates it with the player with the given username"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Success - The item is created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Item::class),
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Failed - Player doesn't exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                        examples = [
                            ExampleObject(
                                value = "App can't found the instance NPC of Player"
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Failed - An internal error happened",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class),
                        examples = [
                            ExampleObject(
                                value = "Something went wrong, please call 0800-EPERS for info"
                            )
                        ]
                    )
                ]
            )
        ]
    )
    @PostMapping("/addToPlayer/{username}")
    fun addToPlayer(@RequestBody item: Item, @PathVariable username: String) : ResponseEntity<Any> {
        return try {
            ResponseEntity.status(200).body(
                itemService.addItemToPlayer(item, username)
            )
        } catch (nf: EntityNotFoundedException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                nf.message
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Something went wrong, please call 0800-EPERS for info"
            )
        }
    }
}