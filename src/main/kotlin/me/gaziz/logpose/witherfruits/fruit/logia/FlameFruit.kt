package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.modifier.createEffect
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.entity.projectile.SmallFireballEntity
import net.minecraft.registry.tag.DamageTypeTags
import net.minecraft.server.network.ServerPlayerEntity

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
    override fun firstAbility(user: ServerPlayerEntity) {
        val smallFireball = SmallFireballEntity(
            user.world,
            user,
            user.rotationVector
        )
        smallFireball.setPosition(user.x, user.getBodyY(0.5) + 0.5, user.z)
        user.world.spawnEntity(smallFireball)
    }

    override fun secondAbility(user: ServerPlayerEntity) {
        val fireball = FireballEntity(
            user.world,
            user,
            user.rotationVector,
            2
        )
        fireball.setPosition(user.x, user.getBodyY(0.5) + 0.5, user.z)
        user.world.spawnEntity(fireball)
    }
}