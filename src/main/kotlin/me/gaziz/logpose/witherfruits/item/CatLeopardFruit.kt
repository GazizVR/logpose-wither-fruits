package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.server.network.ServerPlayerEntity

class CatLeopardFruit: ZoanFruit("cat_leopard_fruit") {
    override var currentForm: Form = Form.Base
    private fun createEffect(
        type: RegistryEntry<StatusEffect>,
        amplifier: Int = 0
    ): StatusEffectInstance {
        return StatusEffectInstance(
            type,
            StatusEffectInstance.INFINITE,
            amplifier,
            false,
            false,
            false
        )
    }
    private fun createModifier(
        attr: RegistryEntry<EntityAttribute>,
        value: Double
    ): Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier> {
        val path = attr.idAsString.substringAfterLast(".")
        return attr to EntityAttributeModifier(
            Initializer.id("leopard_$path").withPrefixedPath("attribute"),
            value,
            EntityAttributeModifier.Operation.ADD_VALUE
        )
    }
    private fun setModifier(
        entity: LivingEntity,
        modifier: Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>
    ) {
        val attr = entity.getAttributeInstance(modifier.first)
        if(attr?.hasModifier(modifier.second.id) == false) {
            attr.addPersistentModifier(modifier.second)
        }
    }
    private fun removeModifier(
        entity: LivingEntity,
        modifier: Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>
    ) {
        val attr = entity.getAttributeInstance(modifier.first)
        attr?.removeModifier(modifier.second)
    }
    private val transformEffects = listOf(
        createEffect(StatusEffects.NIGHT_VISION),
        createEffect(StatusEffects.HUNGER,1),
    )
    private val transformModifiers = mapOf(
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
                    transformModifiers.forEach { (k, v) ->
                        removeModifier(player,k to v)
                    }
                    transformEffects.forEach {
                        player.removeStatusEffect(it.effectType)
                    }
                }
                else -> {}
            }
            currentForm = Form.Base
        } else {
            currentForm = Form.Full
            transformEffects.forEach {
                player.addStatusEffect(it)
            }
            transformModifiers.forEach { (k, v) ->
                setModifier(player,k to v)
            }
        }
    }

    override fun toggleHybridForm(
        player: ServerPlayerEntity
    ) {
        currentForm = if(currentForm == Form.Hybrid) Form.Base else Form.Hybrid
    }
}