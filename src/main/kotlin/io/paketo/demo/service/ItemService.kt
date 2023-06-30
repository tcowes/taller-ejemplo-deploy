package io.paketo.demo.service

import io.paketo.demo.model.Item

interface ItemService : GenericService<Item> {
    fun deleteAll()
    fun addItemToPlayer(item: Item, playerName: String) : Item
    fun itemsOf(username: String): List<Item>
}