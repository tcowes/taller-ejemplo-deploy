package io.paketo.demo.webservice.mapper

import io.paketo.demo.model.Item
import io.paketo.demo.webservice.dto.ItemDTO

object ItemMapper {
    fun fromItemToDTO(item: Item) = ItemDTO(item.name, item.damage, item.relatedClass)
}