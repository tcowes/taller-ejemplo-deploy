package io.paketo.demo.model.exceptions

class InvalidNameException(private val className: String) : Exception() {
    override val message: String
        get() = "You can not create a $className with a blank name"
}