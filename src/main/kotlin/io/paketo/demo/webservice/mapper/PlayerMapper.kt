package io.paketo.demo.webservice.mapper

import io.paketo.demo.model.Item
import io.paketo.demo.model.Player
import io.paketo.demo.webservice.dto.PlayerDTO
import io.paketo.demo.webservice.dto.SamplePlayerDTO

object PlayerMapper {
    fun fromPlayerToDTO(player: Player, items: List<Item>): PlayerDTO {
        val g = if (player.guild != null) player.guild!!.name else "Without guild"
        return PlayerDTO(player.username, player.level,
            items.map { i -> ItemMapper.fromItemToDTO(i) },
            g)
    }

    fun fromPlayerToSampleDTO(player: Player) = SamplePlayerDTO(player.username, player.level)
}