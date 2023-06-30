package io.paketo.demo.service.impl

import io.paketo.demo.model.Item
import io.paketo.demo.model.exceptions.EntityNotFoundedException
import io.paketo.demo.persistence.ItemDAO
import io.paketo.demo.persistence.PlayerDAO
import io.paketo.demo.service.ItemService
import io.paketo.demo.service.TransactionalService
import org.springframework.beans.factory.annotation.Autowired
import java.lang.RuntimeException

@TransactionalService
class ItemServiceImpl : ItemService {
    @Autowired private lateinit var itemDAO: ItemDAO
    @Autowired private lateinit var playerDAO: PlayerDAO
    override fun save(entity: Item): Item {
        return itemDAO.save(entity)
    }

    override fun read(entityName: String): Item {
        if (!itemDAO.existsByName(entityName)) throw EntityNotFoundedException("Item", entityName)
        return itemDAO.findByName(entityName)
    }

    override fun deleteAll() = itemDAO.deleteAll()
    override fun addItemToPlayer(item: Item, playerName: String): Item {
        if (!playerDAO.existsByUsername(playerName)) throw EntityNotFoundedException("Player", playerName)
        item.ownerName = playerName
        return itemDAO.save(item)
    }

    override fun itemsOf(username: String): List<Item> {
        return itemDAO.findByOwnerName(username)
    }
}