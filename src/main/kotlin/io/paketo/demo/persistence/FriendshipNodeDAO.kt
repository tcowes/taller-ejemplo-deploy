package io.paketo.demo.persistence

import io.paketo.demo.model.FriendshipNode
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.data.repository.query.Param

interface FriendshipNodeDAO : Neo4jRepository<FriendshipNode, Long?> {
    fun findByUsername(username: String): FriendshipNode

    @Query("""
        MATCH (p1 { username: ${'$'}playerUsername }),
              (p2 { username: ${'$'}newFriendUsername })
        CREATE (p1)-[:Friend]->(p2),
               (p2)-[:Friend]->(p1)
    """)
    fun addFriendRelationship(
        @Param("playerUsername") playerUsername: String,
        @Param("newFriendUsername") newFriendUsername: String,
    )

    @Query("""
        MATCH (p { username: ${'$'}username })
        MATCH (p)-[:Friend]->(friend)
        RETURN DISTINCT friend.username
    """)
    fun friends(
        @Param("username") username: String,
    ): List<String>

    @Query("""
        MATCH (p {username: ${'$'}playerName})-[rel:Friend]-(f {username: ${'$'}friendName}) 
        RETURN COUNT(rel) > 0
    """)
    fun areFriends(
        @Param("playerName") playerName: String,
        @Param("friendName") friendName: String
    ) : Boolean


    @Query("MATCH (n) DETACH DELETE n")
    fun detachDelete()
}