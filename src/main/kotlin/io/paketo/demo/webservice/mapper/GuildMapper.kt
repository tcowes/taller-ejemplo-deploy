package io.paketo.demo.webservice.mapper

import io.paketo.demo.model.Guild
import io.paketo.demo.webservice.dto.GuildDTO

object GuildMapper {
    fun fromGuildToDTO(guild: Guild): GuildDTO {
        val members = guild.getMembers().map { p -> p.username }.toSet()
        return GuildDTO(guild.name, members)
    }
}