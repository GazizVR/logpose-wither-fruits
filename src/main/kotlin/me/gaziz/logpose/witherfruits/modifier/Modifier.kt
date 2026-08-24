package me.gaziz.logpose.witherfruits.modifier

import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.attribute.EntityAttributeModifier
import net.minecraft.registry.entry.RegistryEntry

data class Modifier(
    val attribute: RegistryEntry<EntityAttribute>,
    val modifier: EntityAttributeModifier
)
