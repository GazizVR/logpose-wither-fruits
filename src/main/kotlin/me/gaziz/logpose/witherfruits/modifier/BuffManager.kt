package me.gaziz.logpose.witherfruits.modifier

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.server.network.ServerPlayerEntity

object BuffManager {
    fun setEffects(
        key: String,
        value: List<StatusEffectInstance>
    ){
        effects[key] = value
    }
    fun removeEffects(player: ServerPlayerEntity){
        effects[player.uuidAsString]?.forEach {
            player.removeStatusEffect(it.effectType)
        }
        effects.remove(player.uuidAsString)
    }
    private val effects = mutableMapOf<String,List<StatusEffectInstance>>()
    fun setModifiers(
        key: String,
        value: List<Modifier>
    ) {
        modifiers[key] = value
    }
    fun removeModifiers(
        player: ServerPlayerEntity,
    ) {
        modifiers[player.uuidAsString]?.forEach { modifier ->
            val attr = player.getAttributeInstance(modifier.attribute)
            attr?.removeModifier(modifier.modifier)
        }
        modifiers.remove(player.uuidAsString)
    }
    private val modifiers = mutableMapOf<String,List<Modifier>>()
    fun initialize() {
        ServerTickEvents.END_SERVER_TICK.register { server ->
            server.playerManager.playerList.forEach { player ->
                effects[player.uuidAsString]?.forEach { effect ->
                    player.addStatusEffect(effect)
                }
                modifiers[player.uuidAsString]?.forEach { modifier ->
                    val attr = player.getAttributeInstance(modifier.attribute)
                    if(attr?.hasModifier(modifier.modifier.id) == false) {
                        attr.addPersistentModifier(modifier.modifier)
                    }
                }
            }
        }
    }
}