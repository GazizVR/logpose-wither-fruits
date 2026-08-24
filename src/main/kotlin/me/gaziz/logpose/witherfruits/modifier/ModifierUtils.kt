package me.gaziz.logpose.witherfruits.modifier

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.registry.entry.RegistryEntry

fun createEffect(
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

fun createModifier(
    attr: RegistryEntry<EntityAttribute>,
    value: Double,
    prefix: String
): Modifier {
    val path = "${prefix}_${attr.idAsString.substringAfterLast(".")}"
    return Modifier(
        attr,
        EntityAttributeModifier(
            Initializer.id(path).withPrefixedPath("attribute"),
            value,
            EntityAttributeModifier.Operation.ADD_VALUE
        )
    )
}
