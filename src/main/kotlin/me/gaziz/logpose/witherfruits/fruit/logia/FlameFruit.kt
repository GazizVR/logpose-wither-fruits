package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.PlayerManager
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import me.gaziz.logpose.witherfruits.modifier.createEffect
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.tag.DamageTypeTags

class FlameFruit: LogiaFruit("flame_fruit") {
    private val effects = listOf(
        createEffect(StatusEffects.FIRE_RESISTANCE)
    )
    private val immuneDamageTypes = setOf(
        DamageTypeTags.IS_EXPLOSION,
        DamageTypeTags.IS_PROJECTILE,
        DamageTypeTags.IS_FIRE,
        DamageTypeTags.IS_PLAYER_ATTACK
    )
    init {
        users.forEach { uuidStr ->
            BuffManager.setEffects(uuidStr,effects)
            PlayerManager.setDamageTypes(uuidStr,immuneDamageTypes)
        }
    }

    override fun onEat(user: LivingEntity){
        val uuidStr = user.uuidAsString
        if(users.contains(uuidStr)) {
            BuffManager.removeEffects(user)
            PlayerManager.removeDamageTypes(uuidStr)
        } else {
            BuffManager.setEffects(uuidStr,effects)
            PlayerManager.setDamageTypes(uuidStr,immuneDamageTypes)
        }
    }
}