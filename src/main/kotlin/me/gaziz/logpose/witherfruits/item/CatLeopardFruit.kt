package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.ability.AbilityManager
import me.gaziz.logpose.witherfruits.ability.Modifier
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity

class CatLeopardFruit: ZoanFruit("cat_leopard_fruit") {
    override var currentForm: Form = Form.Base

    private val transformEffects = listOf(
        AbilityManager.createEffect(StatusEffects.NIGHT_VISION),
        AbilityManager.createEffect(StatusEffects.HUNGER,1),
    )
    private fun createModifier(
        attr: RegistryEntry<EntityAttribute>,
        value: Double,
    ): Modifier {
        val prefix = "leopard"
        return AbilityManager.createModifier(attr, value, prefix)
    }
    private val transformModifiers = listOf(
        createModifier(EntityAttributes.GENERIC_SCALE,-0.34),
        createModifier(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED,-1.0),
        createModifier(EntityAttributes.GENERIC_ARMOR,4.0),
        //Damage
        createModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,3.0),
        createModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,-0.5),
        //Movement
        createModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,0.04),
        createModifier(EntityAttributes.PLAYER_SNEAKING_SPEED,0.3),
        createModifier(EntityAttributes.GENERIC_STEP_HEIGHT,0.4),
        //Jump
        createModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,0.2),
        createModifier(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE,2.5),
        createModifier(EntityAttributes.GENERIC_FALL_DAMAGE_MULTIPLIER,-0.2),
    )

    override fun toggleTransform(
        player: ServerPlayerEntity
    ) {
        if(currentForm == Form.Full){
            when(currentForm) {
                Form.Full -> {
                    AbilityManager.removeModifiers(player)
                    AbilityManager.removeEffects(player)
                }
                else -> {}
            }
            currentForm = Form.Base
        } else {
            currentForm = Form.Full
            AbilityManager.setEffects(player.uuidAsString,transformEffects)
            AbilityManager.setModifiers(player.uuidAsString,transformModifiers)
        }
    }

    override fun toggleHybridForm(
        player: ServerPlayerEntity
    ) {
        currentForm = if(currentForm == Form.Hybrid) Form.Base else Form.Hybrid
    }
}