package io.paketo.demo.webservice.controller

import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.service.GuildService
import io.paketo.demo.webservice.mapper.GuildMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RequestMapping("/guild")
@RestController
class GuildController {
    @Autowired private lateinit var guildService: GuildService

    @PostMapping("/create/{guildName}/leader/{username}")
    fun createGuild(@PathVariable username: String, @PathVariable guildName: String) : ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(
                GuildMapper.fromGuildToDTO(guildService.createGuild(guildName, username))
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

    @PostMapping("/{guildName}/addMember/{username}")
    fun addMember(@PathVariable guildName: String, @PathVariable username: String) : ResponseEntity<Any> {
        return try {
            guildService.addMember(guildName, username)
            ResponseEntity.ok().body(
                GuildMapper.fromGuildToDTO(guildService.read(guildName))
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

    @GetMapping("/{guildName}")
    fun getGuild(@PathVariable guildName: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok().body(
                GuildMapper.fromGuildToDTO(guildService.read(guildName))
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