package me.gaziz.logpose.witherfruits

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry

object UsersManager {
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
    private val seaWeaknessId = Initializer.id("sea_weakness")
    fun initialize(){
        ServerTickEvents.END_SERVER_TICK.register { server ->
            val state = PersistFruitsState().getPersistFruitsState(server)
            server.playerManager.playerList.forEach { player ->
                if(state.getFruits().containsKey(player.uuidAsString)) {
                    val attribute = player.getAttributeInstance(EntityAttributes.GENERIC_GRAVITY)
                    if(player.isTouchingWater) {
                        player.setJumping(false)
                        hasNegativeEffects = true
                        negativeEffects.forEach {
                            player.addStatusEffect(it)
                        }
                        if(attribute?.getModifier(seaWeaknessId) == null) {
                            val modifier = EntityAttributeModifier(
                                seaWeaknessId,
                                0.5,
                                EntityAttributeModifier.Operation.ADD_VALUE
                            )
                            attribute?.addPersistentModifier(modifier)
                        }
                    } else {
                        if(hasNegativeEffects) {
                            hasNegativeEffects = false
                            negativeEffects.forEach {
                                player.removeStatusEffect(it.effectType)
                            }
                            attribute?.removeModifier(seaWeaknessId)
                        }
                    }
                }
            }
        }
    }
}