package io.paketo.demo.model.exceptions

class FriendshipException(private val msg: String) : Exception() {
    override val message: String
        get() = msg
}
