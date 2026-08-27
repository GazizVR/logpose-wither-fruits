package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.EntityType
import net.minecraft.entity.damage.DamageType
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity

object PlayerManager {
    private val immuneDamageTypes = mutableMapOf<String,List<TagKey<DamageType>>>()
    fun getDamageTypes(key: String): List<TagKey<DamageType>>? = immuneDamageTypes[key]
    fun setDamageTypes(
        key: String,
        value: List<TagKey<DamageType>>
    ) {
        immuneDamageTypes[key] = value
    }
    fun removeDamageTypes(key: String) {
        immuneDamageTypes.remove(key)
    }

    private val entityTypes = mutableMapOf<String, EntityType<*>>()
    fun setEntityTypeAndSend(
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