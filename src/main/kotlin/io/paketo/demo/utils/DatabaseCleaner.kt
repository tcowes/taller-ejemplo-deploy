package io.paketo.demo.utils

import io.paketo.demo.persistence.FriendshipNodeDAO
import io.paketo.demo.persistence.ItemDAO
import io.paketo.demo.service.TransactionalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@TransactionalService
class CleanerImpl : Cleaner {
    @Autowired lateinit var dataDAO: DataDAO
    @Autowired lateinit var itemDAO: ItemDAO
    @Autowired lateinit var friendshipNodeDAO: FriendshipNodeDAO
    override fun cleanDB() { dataDAO.clear(); itemDAO.deleteAll(); friendshipNodeDAO.detachDelete() }
}

interface Cleaner { fun cleanDB() }

@Component
open class DataDAO {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun clear() {
        val nombreDeTablas = entityManager.createNativeQuery("show tables").resultList
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0;").executeUpdate()
        nombreDeTablas.forEach { result ->
            var tabla = ""
            when(result){
                is String -> tabla = result
                is Array<*> -> tabla= result[0].toString()
            }
            entityManager.createNativeQuery("truncate table $tabla").executeUpdate()
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1;").executeUpdate()
    }
}
