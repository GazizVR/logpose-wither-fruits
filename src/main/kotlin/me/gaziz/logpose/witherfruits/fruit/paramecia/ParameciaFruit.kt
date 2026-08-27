package me.gaziz.logpose.witherfruits.fruit.paramecia

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.PlayerManager
import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import me.gaziz.logpose.witherfruits.modifier.Modifier
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.entity.damage.DamageType
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.registry.tag.TagKey
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Rarity

abstract class ParameciaFruit(path: String): WitherFruit(
    path = path,
    rarity = Rarity.UNCOMMON,
    tooltipKey = ModFruits.parameciaTooltipKey
) {
    abstract val immuneDamageTypes: List<TagKey<DamageType>>
    abstract val buffEffects: List<StatusEffectInstance>
    abstract val modifiers: List<Modifier>

    init {
        ServerLifecycleEvents.SERVER_STARTED.register {
            val state = PersistFruitsState().getPersistFruitsState(it)
            state.getFruits().forEach { (key, value) ->
                if(value == this) {
                    BuffManager.setEffects(key,buffEffects)
                    PlayerManager.setDamageTypes(key,immuneDamageTypes)
                    BuffManager.setModifiers(key,modifiers)
                }
            }
        }
    }

    override fun onRemove(user: ServerPlayerEntity) {
        val key = user.uuidAsString
        PlayerManager.removeDamageTypes(key)
        BuffManager.removeModifiers(user)
        BuffManager.removeEffects(user)
    }

    override fun onEat(user: ServerPlayerEntity) {
        val key = user.uuidAsString
        BuffManager.setEffects(key,buffEffects)
        PlayerManager.setDamageTypes(key,immuneDamageTypes)
        BuffManager.setModifiers(key,modifiers)
    }
}