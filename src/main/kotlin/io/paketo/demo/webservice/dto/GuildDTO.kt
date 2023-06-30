package io.paketo.demo.webservice.dto

data class GuildDTO(
    val name: String,
    val members: Set<String>
)
