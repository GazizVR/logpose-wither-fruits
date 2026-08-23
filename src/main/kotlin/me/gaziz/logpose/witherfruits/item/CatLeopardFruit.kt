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
import net.minecraft.text.Text

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
    private val transformEffects = listOf(
        createEffect(StatusEffects.NIGHT_VISION),
        createEffect(StatusEffects.RESISTANCE)
    )
    private val transformModifiers = mapOf(
        EntityAttributes.PLAYER_SNEAKING_SPEED to EntityAttributeModifier(
            Initializer.id("leopard_sneaking_speed"),
            0.2,
            EntityAttributeModifier.Operation.ADD_VALUE
        )
    )
    private fun updateModifier(
        entity: LivingEntity,
        modifier: Pair<RegistryEntry<EntityAttribute>, EntityAttributeModifier>
    ) {
        val attr = entity.getAttributeInstance(modifier.first)
        if(attr?.hasModifier(modifier.second.id) == false) {
            attr.addPersistentModifier(modifier.second)
        } else {
            attr?.removeModifier(modifier.second)
        }
    }
    override fun transform(user: LivingEntity) {
        currentForm = Form.Full
        transformEffects.forEach {
            user.addStatusEffect(it)
        }
        transformModifiers.forEach { (k, v) ->
            updateModifier(user,k to v)
        }
    }
    override fun hybridTransform(user: LivingEntity) {
        currentForm = Form.Hybrid
    }
    override fun baseTransform(user: LivingEntity) {
        when(currentForm) {
            Form.Full -> {
                transformModifiers.forEach { (k, v) ->
                    updateModifier(user,k to v)
                }
                transformEffects.forEach {
                    user.removeStatusEffect(it.effectType)
                }
            }
            else -> {}
        }
        currentForm = Form.Base
    }

    fun toggleTransform(
        player: ServerPlayerEntity
    ) {
        if(currentForm == Form.Full) baseTransform(player) else transform(player)
        player.sendMessage(
            Text.literal(currentForm.name),
            true
        )
    }
}