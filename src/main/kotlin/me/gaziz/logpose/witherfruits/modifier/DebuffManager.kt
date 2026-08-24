package me.gaziz.logpose.witherfruits.modifier

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry

object DebuffManager {
    private val effect: (RegistryEntry<StatusEffect>) -> StatusEffectInstance = {
        createEffect(it, StatusEffectInstance.MAX_AMPLIFIER)
    }
    private val effects = listOf(effect(StatusEffects.WEAKNESS))
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
                        effects.forEach { player.addStatusEffect(it) }
                    } else {
                        effects.forEach { player.removeStatusEffect(it.effectType) }
                    }
                }
            }
        }
    }
}