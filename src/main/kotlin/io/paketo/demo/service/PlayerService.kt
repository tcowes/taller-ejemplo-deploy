package io.paketo.demo.service

import io.paketo.demo.model.Player

interface PlayerService : GenericService<Player> {
    fun addFriend(playerName: String, friendName: String)
    fun friends(playerName: String) : List<Player>
}
