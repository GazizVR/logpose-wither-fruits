package me.gaziz.logpose.witherfruits.ability

import me.gaziz.logpose.witherfruits.Initializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity

object AbilityManager {
    fun createEffect(
        type: RegistryEntry<StatusEffect>,
        amplifier: Int = 0
    ): StatusEffectInstance {
        return StatusEffectInstance(
            type,
            StatusEffectInstance.INFINITE,
            amplifier,
            false,
            false,
            false
        )
    }
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
    fun createModifier(
        attr: RegistryEntry<EntityAttribute>,
        value: Double,
        prefix: String
    ): Modifier {
        val path = "${prefix}_${attr.idAsString.substringAfterLast(".")}"
        return Modifier(
            attr,
            EntityAttributeModifier(
                Initializer.id(path).withPrefixedPath("attribute"),
                value,
                EntityAttributeModifier.Operation.ADD_VALUE
            )
        )
    }
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