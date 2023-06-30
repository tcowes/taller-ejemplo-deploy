package io.paketo.demo.persistence

import io.paketo.demo.model.Player
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerDAO : JpaRepository<Player, Long> {
    fun findAllByUsernameIn(nicknames: List<String>): List<Player>
    fun existsByUsername(nickname: String): Boolean
    fun findByUsername(nickname: String): Player
}