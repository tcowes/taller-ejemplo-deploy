package io.paketo.demo.service

import io.paketo.demo.model.Guild

interface GuildService : GenericService<Guild> {
    fun addMember(guildName: String, playerName: String)
    fun createGuild(name: String, playerName: String): Guild
}
