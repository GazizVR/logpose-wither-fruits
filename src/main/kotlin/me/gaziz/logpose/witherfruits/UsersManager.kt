package me.gaziz.logpose.witherfruits

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

object UsersManager {
    var fruits: Map<String,WitherFruit> = emptyMap()
    private set
    fun setFruit(
        uuid: String,
        f: WitherFruit
    ) {
        fruits = fruits + (uuid to f)
    }
    fun removeFruit(uuid: String) {
        fruits = fruits - uuid
    }
    private val effectEntry = StatusEffects.SLOWNESS
    private val statusEffect = StatusEffectInstance(
        effectEntry,
        StatusEffectInstance.INFINITE,
        StatusEffectInstance.MAX_AMPLIFIER,
        false,
        false,
        false
    )
    private var isEffectSet = false
    fun initLoop(){
        ServerTickEvents.END_SERVER_TICK.register { server ->
            server.playerManager.playerList.forEach { player ->
                if(fruits.containsKey(player.uuidAsString)) {
                    if(player.isTouchingWater) {
                        isEffectSet = true
                        player.addStatusEffect(statusEffect)
                    } else {
                        if(isEffectSet) {
                            isEffectSet = false
                            player.removeStatusEffect(effectEntry)
                        }
                    }
                }
            }
        }
    }
}