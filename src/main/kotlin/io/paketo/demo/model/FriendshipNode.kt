package io.paketo.demo.model

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node

@Node
class FriendshipNode(val username: String) {
    @Id
    @GeneratedValue
    var id : Long? = null
}