package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.PlayerManager
import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.entity.damage.DamageType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Rarity

abstract class LogiaFruit(path: String): WitherFruit(
    path = path,
    rarity = Rarity.EPIC,
    tooltipKey = ModFruits.logiaTooltipKey
) {
    protected abstract val buffEffects: List<StatusEffectInstance>
    protected abstract val immuneDamageTypes: List<TagKey<DamageType>>

    init {
        ServerLifecycleEvents.SERVER_STARTED.register { server ->
            val state = PersistFruitsState().getPersistFruitsState(server)
            state.getFruits().forEach { (key, value) ->
                if(value == this) {
                    BuffManager.setEffects(key,buffEffects)
                    PlayerManager.setDamageTypes(key,immuneDamageTypes)
                }
            }
        }
    }

    override fun onRemove(user: ServerPlayerEntity) {
        BuffManager.removeEffects(user)
        PlayerManager.removeDamageTypes(user.uuidAsString)
    }

    override fun onEat(user: ServerPlayerEntity){
        val uuidStr = user.uuidAsString
        val state = PersistFruitsState().getPersistFruitsState(user.server)
        if(!state.getFruits().contains(uuidStr)) {
            BuffManager.setEffects(uuidStr,buffEffects)
            PlayerManager.setDamageTypes(uuidStr,immuneDamageTypes)
        }
    }
    abstract fun firstAbility(user: ServerPlayerEntity)
    abstract fun secondAbility(user: ServerPlayerEntity)
}