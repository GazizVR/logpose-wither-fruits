package me.gaziz.logpose.witherfruits.modifier

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry

object DebuffManager {
    private val effect: (RegistryEntry<StatusEffect>) -> StatusEffectInstance = {
        createEffect(it, StatusEffectInstance.MAX_AMPLIFIER)
    }
    private val effects = listOf(
        effect(StatusEffects.BLINDNESS),
        effect(StatusEffects.WEAKNESS),
    )
    private var hasEffects = false
    private val modifier: (RegistryEntry<EntityAttribute>) -> Modifier = {
        val prefix = "debuff_manager"
        createModifier(it,-1.0,prefix)
    }
    private val modifiers = listOf(
        modifier(EntityAttributes.GENERIC_STEP_HEIGHT),
        modifier(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED),
    )
    fun initialize(){
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            val player = handler.player
            val state = PersistFruitsState().getPersistFruitsState(server)
            val fruit = state.getFruits()[player.uuidAsString]
            NetworkManager.sendCanSwimS2C(player,fruit == null)
        }
        ServerTickEvents.END_SERVER_TICK.register { server ->
            val state = PersistFruitsState().getPersistFruitsState(server)
            server.playerManager.playerList.forEach { player ->
                val playerFruit = state.getFruits()[player.uuidAsString]
                if(playerFruit != null) {
                    if(player.isTouchingWater) {
                        player.setJumping(false)
                        hasEffects = true
                        effects.forEach { player.addStatusEffect(it) }
                        modifiers.forEach {
                            val attr = player.getAttributeInstance(it.attribute)
                            if(attr?.hasModifier(it.modifier.id) == false){
                                attr.addPersistentModifier(it.modifier)
                            }
                        }
                    } else {
                        if(hasEffects) {
                            hasEffects = false
                            effects.forEach { player.removeStatusEffect(it.effectType) }
                            modifiers.forEach {
                                val attr = player.getAttributeInstance(it.attribute)
                                attr?.removeModifier(it.modifier)
                            }
                        }
                    }
                }
            }
        }
    }
}