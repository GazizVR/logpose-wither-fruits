package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.modifier.createEffect
import net.minecraft.SharedConstants
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.entity.projectile.SmallFireballEntity
import net.minecraft.registry.tag.DamageTypeTags
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

class FlameFruit: LogiaFruit("flame_fruit") {
    override fun onRemove(user: ServerPlayerEntity) {
        super.onRemove(user)
        val key = user.uuidAsString
        globalCooldown.remove(key)
        firstCooldown.remove(key)
        secondCooldown.remove(key)
    }
    override val buffEffects = listOf(
        createEffect(StatusEffects.FIRE_RESISTANCE)
    )
    override val immuneDamageTypes = listOf(
        DamageTypeTags.IS_EXPLOSION,
        DamageTypeTags.IS_PROJECTILE,
        DamageTypeTags.IS_FIRE,
        DamageTypeTags.IS_LIGHTNING
    )

    private val cooldownText = Text.literal("On cooldown")
    private val globalCooldown = mutableMapOf<String,Int>()

    private val firstCooldown = mutableMapOf<String, Int>()
    override fun firstAbility(user: ServerPlayerEntity){
        val key = user.uuidAsString
        val currentTick = user.server.ticks

        val globalCD = globalCooldown[key] ?: 0
        if(currentTick < globalCD) return

        val cooldown = firstCooldown[key] ?: 0
        if(currentTick < cooldown) {
            user.sendMessage(cooldownText,true)
            return
        }
        globalCooldown[key] = currentTick + (SharedConstants.TICKS_PER_SECOND/2)
        firstCooldown[key] = currentTick + (SharedConstants.TICKS_PER_SECOND*3)
        val smallFireball = SmallFireballEntity(
            user.world,
            user,
            user.rotationVector
        )
        smallFireball.setPosition(user.x, user.getBodyY(0.5) + 0.5, user.z)
        user.world.spawnEntity(smallFireball)
    }

    private val secondCooldown = mutableMapOf<String, Int>()
    override fun secondAbility(user: ServerPlayerEntity) {
        val key = user.uuidAsString
        val currentTick = user.server.ticks

        val globalCD = globalCooldown[key] ?: 0
        if(currentTick < globalCD) return

        val cooldown = secondCooldown[key] ?: 0
        if(currentTick < cooldown) {
            user.sendMessage(cooldownText,true)
            return
        }
        globalCooldown[key] = currentTick + (SharedConstants.TICKS_PER_SECOND/2)
        secondCooldown[key] = currentTick + (SharedConstants.TICKS_PER_SECOND*6)
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