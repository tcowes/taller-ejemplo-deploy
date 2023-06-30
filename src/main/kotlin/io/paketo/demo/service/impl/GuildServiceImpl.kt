package io.paketo.demo.service.impl

import io.paketo.demo.model.Guild
import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.persistence.GuildDAO
import io.paketo.demo.persistence.PlayerDAO
import io.paketo.demo.service.GuildService
import io.paketo.demo.service.TransactionalService
import org.springframework.beans.factory.annotation.Autowired

@TransactionalService
class GuildServiceImpl : GuildService {
    @Autowired private lateinit var playerDAO: PlayerDAO
    @Autowired private lateinit var guildDAO: GuildDAO
    override fun addMember(guildName: String, playerName: String) {
        val guild = read(guildName)
        if (!playerDAO.existsByUsername(playerName)) throw EntityNotFoundedException("Player", playerName)
        val player = playerDAO.findByUsername(playerName)
        guild.addMember(player)
        save(guild)
    }

    override fun createGuild(name: String, playerName: String): Guild {
        if (!playerDAO.existsByUsername(playerName)) throw EntityNotFoundedException("Player", playerName)
        return save(Guild(name, playerDAO.findByUsername(playerName)))
    }

    override fun save(entity: Guild): Guild {
        return guildDAO.save(entity)
    }

    override fun read(entityName: String): Guild {
        if (!guildDAO.existsByName(entityName)) throw EntityNotFoundedException("Guild", entityName)
        return guildDAO.findByName(entityName)
    }

}