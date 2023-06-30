package io.paketo.demo.service.impl

import io.paketo.demo.model.FriendshipNode
import io.paketo.demo.model.Guild
import io.paketo.demo.model.Player
import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.model.exceptions.FriendshipException
import io.paketo.demo.persistence.FriendshipNodeDAO
import io.paketo.demo.persistence.PlayerDAO
import io.paketo.demo.service.PlayerService
import io.paketo.demo.service.TransactionalService
import org.springframework.beans.factory.annotation.Autowired
import java.lang.RuntimeException

@TransactionalService
class PlayerServiceImpl : PlayerService {
    @Autowired private lateinit var playerDAO : PlayerDAO
    @Autowired private lateinit var friendshipNodeDAO: FriendshipNodeDAO
    override fun save(entity: Player): Player {
        friendshipNodeDAO.save(FriendshipNode(entity.username))
        return playerDAO.save(entity)
    }

    override fun read(entityName: String): Player {
        if (!playerDAO.existsByUsername(entityName)) throw EntityNotFoundedException("Player", entityName)
        return playerDAO.findByUsername(entityName)
    }

    override fun addFriend(playerName: String, friendName: String) {
        // Chequeo que existan ambos
        if (!playerDAO.existsByUsername(playerName)) throw EntityNotFoundedException("Player", playerName)
        if (!playerDAO.existsByUsername(friendName)) throw EntityNotFoundedException("Player", friendName)
        // Chequeo que no sean la misma persona
        if (playerName == friendName) throw FriendshipException("A player can't be friend of herself </3")
        // Chequeo que no sean amigos actualmente
        if (friendshipNodeDAO.areFriends(playerName, friendName)) throw FriendshipException("Those players are already friends")

        friendshipNodeDAO.addFriendRelationship(playerName, friendName)
    }

    override fun friends(playerName: String): List<Player> {
        val friendsNames = friendshipNodeDAO.friends(playerName)
        return playerDAO.findAllByUsernameIn(friendsNames)
    }

}