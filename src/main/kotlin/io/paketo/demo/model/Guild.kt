package io.paketo.demo.model

import io.paketo.demo.model.exceptions.InvalidNameException
import javax.persistence.*

@Entity
class Guild(@Column(nullable = false, unique = true) val name: String,
            @OneToOne
            val leader: Player) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "guild")
    private val members = mutableSetOf<Player>()

    fun membersAmount() = members.size
    fun getMembers() = members

    fun addMember(player: Player) {
        if (player.guild != null) {
            player.guild!!.removePlayer(player)
        }
        player.updateGuild(this)
        members.add(player)
    }

    fun removePlayer(player: Player) {
        members.remove(player)
        player.guild = null
    }

    init {
        if (name.isBlank()) { throw InvalidNameException(this.javaClass.simpleName) }
        addMember(leader)
    }
}
