package io.paketo.demo.webservice.controller

import io.paketo.demo.model.Player
import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.model.exceptions.FriendshipException
import io.paketo.demo.service.ItemService
import io.paketo.demo.service.PlayerService
import io.paketo.demo.webservice.mapper.PlayerMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin
@RequestMapping("/player")
@RestController
class PlayerController {
    @Autowired private lateinit var playerService: PlayerService
    @Autowired private lateinit var itemService: ItemService

    private val logger = LoggerFactory.getLogger(PlayerController::class.java)

    @PostMapping("/register/{username}")
    fun register(@PathVariable username: String) : ResponseEntity<Any> {
        return try {
            val player = playerService.save(Player(username))
            val items = itemService.itemsOf(player.username)
            logger.info("The player $username was created successfully")

            ResponseEntity.status(200).body(
                PlayerMapper.fromPlayerToDTO(player, items)
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Something went wrong, please call 0800-EPERS for info"
            )
        }
    }

    @GetMapping("/{username}")
    fun recover(@PathVariable username: String) : ResponseEntity<Any> {
        return try {
            val player = playerService.read(username)
            val items = itemService.itemsOf(player.username)
            ResponseEntity.status(200).body(
                PlayerMapper.fromPlayerToDTO(player, items)
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

    @PostMapping("/addFriend/{playerName}/{friendName}")
    fun addFriend(@PathVariable playerName: String, @PathVariable friendName: String) : ResponseEntity<Any> {
        return try {
            playerService.addFriend(playerName, friendName)
            ResponseEntity.status(200).body(
                "Now $playerName and $friendName are friends"
            )
        } catch (nf: EntityNotFoundedException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                nf.message
            )
        } catch (fe: FriendshipException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                fe.message
            )
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Something went wrong, please call 0800-EPERS for info"
            )
        }
    }

    @GetMapping("/friendsOf/{username}")
    fun friends(@PathVariable username: String) : ResponseEntity<Any> {
        return ResponseEntity.status(200).body(
            playerService.friends(username).map { p -> PlayerMapper.fromPlayerToSampleDTO(p) }
        )
    }

}