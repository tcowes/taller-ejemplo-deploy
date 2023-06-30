package io.paketo.demo.webservice.dto

data class PlayerDTO(
    val username: String,
    val level: Int,
    val items: List<ItemDTO>,
    val guild: String
)