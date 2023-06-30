package io.paketo.demo.persistence

import io.paketo.demo.model.Item
import org.springframework.data.mongodb.repository.MongoRepository

interface ItemDAO : MongoRepository<Item, String> {
    fun findByOwnerName(username: String): List<Item>
    fun existsByName(itemName: String): Boolean
    fun findByName(itemName: String): Item
}