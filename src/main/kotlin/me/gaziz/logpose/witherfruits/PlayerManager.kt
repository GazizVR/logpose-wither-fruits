package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.EntityType
import net.minecraft.server.network.ServerPlayerEntity

object PlayerManager {
    private val entityTypes = mutableMapOf<String, EntityType<*>>()
    fun setEntityType(
        player: ServerPlayerEntity,
        value: EntityType<*>
    ) {
        val key = player.uuidAsString
        entityTypes[key] = value
        player.server.playerManager.playerList.forEach {
            NetworkManager.sendEntityTypeS2C(
                player = it,
                uuidStr = key,
                entityType = value
            )
        }
    }
    fun removeEntityType(
        player: ServerPlayerEntity
    ) {
        val key = player.uuidAsString
        entityTypes.remove(key)
        player.server.playerManager.playerList.forEach {
            NetworkManager.sendEntityTypeS2C(
                player = it,
                uuidStr = key,
                entityType = null
            )
        }
    }
    fun initialize() {
        ServerPlayConnectionEvents.JOIN.register { h, _, _ ->
            val player = h.player
            entityTypes.forEach { (k, v) ->
                NetworkManager.sendEntityTypeS2C(player,k,v)
            }
        }
    }
}