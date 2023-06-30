package io.paketo.demo.model

import io.paketo.demo.model.exceptions.DamageException
import io.paketo.demo.model.exceptions.InvalidNameException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModelTest {
    @Test
    fun player_NoSePermitenNombresEnBlanco() {
        val expectedMsg = InvalidNameException(Player::class.java.simpleName).message
        println(expectedMsg)
        try { Player("") } catch (e: Throwable) {
            Assertions.assertEquals(expectedMsg, e.message!!)
        }
    }

    @Test
    fun guild_NoSePermitenNombresEnBlanco() {
        val expectedMsg = InvalidNameException(Guild::class.java.simpleName).message
        println(expectedMsg)
        try { Guild("", Player("NPC")) } catch (e: Throwable) {
            Assertions.assertEquals(expectedMsg, e.message!!)
        }
    }

    @Test
    fun guild_RegistraCorrectamenteLaCantidadDeJugadores() {
        val players = listOf(Player("Asesino"), Player("Guerrero"), Player("Maguito"))
        val guild = Guild("Estereotipo RPG", players[0])
        Assertions.assertEquals(1, guild.membersAmount())
        guild.addMember(players[1])
        guild.addMember(players[2])
        Assertions.assertEquals(3, guild.membersAmount())
        players.forEach { p -> Assertions.assertEquals(guild, p.guild!!) }
    }

    @Test
    fun guild_NoRegistraDuplicados() {
        val player = Player("NPC")
        val guild = Guild("Sindicato de NPCs", player)
        guild.addMember(player)
        Assertions.assertEquals(1, guild.membersAmount())
    }

    @Test
    fun item_DañoNoValido() {
        val exceptionError = DamageException().message
        try { Item("Espada del Vacio", 0, RPGClass.SUPPORT) } catch (e: Exception) {
            Assertions.assertEquals(exceptionError, e.message)
        }
        try { Item("Antitesis de armas", -10, RPGClass.SUPPORT) } catch (e: Exception) {
            Assertions.assertEquals(exceptionError, e.message)
        }
    }

}