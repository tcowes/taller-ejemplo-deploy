package io.paketo.demo.model.exceptions

class DamageException : Exception() {
    override val message: String
        get() = "The damage of the item needs to be greater than zero or equals if his related class is Tank or Support"
}