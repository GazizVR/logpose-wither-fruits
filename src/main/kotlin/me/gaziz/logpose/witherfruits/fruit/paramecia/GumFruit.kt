package me.gaziz.logpose.witherfruits.fruit.paramecia

import me.gaziz.logpose.witherfruits.modifier.Modifier
import me.gaziz.logpose.witherfruits.modifier.createEffect
import me.gaziz.logpose.witherfruits.modifier.createModifier
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.registry.entry.RegistryEntry
import net.minecraft.registry.tag.DamageTypeTags

class GumFruit: ParameciaFruit("gum_fruit") {
    override val immuneDamageTypes = listOf(
        DamageTypeTags.IS_LIGHTNING,
        DamageTypeTags.IS_FALL,
        DamageTypeTags.IS_EXPLOSION
    )
    override val buffEffects: List<StatusEffectInstance> = listOf(
        createEffect(StatusEffects.JUMP_BOOST,1)
    )
    private fun createModifier(
        attr: RegistryEntry<EntityAttribute>,
        value: Double,
    ): Modifier {
        val prefix = "gum"
        return createModifier(attr, value, prefix)
    }
    override val modifiers: List<Modifier> = listOf(
        createModifier(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE,1.6),
        createModifier(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,2.0),
    )
}