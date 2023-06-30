package io.paketo.demo.service

interface GenericService<T> {
    fun save(entity: T): T
    fun read(entityName: String): T
}