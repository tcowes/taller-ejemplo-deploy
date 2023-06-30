package io.paketo.demo.model.exceptions

class EntityNotFoundedException(private val className: String, val instanceName: String) : Exception() {
    override val message: String
        get() = "App can't found the instance $instanceName of $className"
}
