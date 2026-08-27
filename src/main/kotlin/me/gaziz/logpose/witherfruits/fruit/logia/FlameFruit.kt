package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.modifier.createEffect
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.tag.DamageTypeTags

class FlameFruit: LogiaFruit("flame_fruit") {
    override val buffEffects = listOf(
        createEffect(StatusEffects.FIRE_RESISTANCE)
    )
    override val immuneDamageTypes = listOf(
        DamageTypeTags.IS_EXPLOSION,
        DamageTypeTags.IS_PROJECTILE,
        DamageTypeTags.IS_FIRE,
        DamageTypeTags.IS_PLAYER_ATTACK
    )
}