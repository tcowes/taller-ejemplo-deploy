package io.paketo.demo.webservice.dto

import io.paketo.demo.model.RPGClass

data class ItemDTO(
    val name: String,
    val damage: Int,
    val relatedClass: RPGClass,
)