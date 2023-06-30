package io.paketo.demo.model

import io.paketo.demo.model.exceptions.InvalidNameException
import javax.persistence.*

@Entity
class Player(@Column(unique = true, nullable = false) val username: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var level = 1

    @ManyToOne
    var guild : Guild? = null

    fun updateGuild(guild: Guild) {
        this.guild = guild
    }

    init {
        if (username.isBlank()) { throw InvalidNameException(this.javaClass.simpleName) }
    }
}