package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry

object DebufManager {
    private val negativeEffect: (RegistryEntry<StatusEffect>) -> StatusEffectInstance = {
        StatusEffectInstance(
            it,
            StatusEffectInstance.INFINITE,
            StatusEffectInstance.MAX_AMPLIFIER,
            false,
            false,
            false
        )
    }
    private val negativeEffects = listOf(
        negativeEffect(StatusEffects.SLOWNESS),
        negativeEffect(StatusEffects.BLINDNESS),
        negativeEffect(StatusEffects.MINING_FATIGUE),
        negativeEffect(StatusEffects.WEAKNESS),
    )
    private var hasNegativeEffects = false
    fun initialize(){
        ServerPlayConnectionEvents.JOIN.register { handler, _, server ->
            val player = handler.player
            val state = PersistFruitsState().getPersistFruitsState(server)
            val fruit = state.getFruits()[player.uuidAsString]
            NetworkManager.sendCanSwimS2C(
                player,
                fruit == null,
            )
        }
        ServerTickEvents.END_SERVER_TICK.register { server ->
            val state = PersistFruitsState().getPersistFruitsState(server)
            server.playerManager.playerList.forEach { player ->
                val playerFruit = state.getFruits()[player.uuidAsString]
                if(playerFruit != null) {
                    if(player.isTouchingWater) {
                        player.setJumping(false)
                        hasNegativeEffects = true
                        negativeEffects.forEach {
                            player.addStatusEffect(it)
                        }
                    } else {
                        if(hasNegativeEffects) {
                            hasNegativeEffects = false
                            negativeEffects.forEach {
                                player.removeStatusEffect(it.effectType)
                            }
                        }
                    }
                }
            }
        }
    }
}