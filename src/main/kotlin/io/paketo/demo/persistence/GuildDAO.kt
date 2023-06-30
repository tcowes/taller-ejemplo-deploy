package io.paketo.demo.persistence

import io.paketo.demo.model.Guild
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GuildDAO : JpaRepository<Guild, Long> {
    fun findByName(guildName: String): Guild
    fun existsByName(guildName: String): Boolean
}