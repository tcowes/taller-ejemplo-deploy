package io.paketo.demo.model

import io.paketo.demo.model.exceptions.DamageException
import io.paketo.demo.model.exceptions.InvalidNameException
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.*

@Document
class Item(val name: String, val damage: Int, val relatedClass: RPGClass) {
    var ownerName: String = "Without owner"

    @Id
    var id: String? = null

    init {
        when (relatedClass) {
            RPGClass.TANK, RPGClass.SUPPORT -> if (damage < 0) throw DamageException()
            else -> if (damage < 1) throw DamageException()
        }
        if (name.isBlank()) throw InvalidNameException(Item::class.java.simpleName)
    }

}

enum class RPGClass {
    MELEE, RANGE, TANK, MAGE, SUPPORT
}