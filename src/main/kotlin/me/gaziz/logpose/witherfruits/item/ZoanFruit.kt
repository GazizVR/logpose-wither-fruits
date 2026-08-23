package me.gaziz.logpose.witherfruits.item

import net.minecraft.entity.LivingEntity
import net.minecraft.util.Rarity

abstract class ZoanFruit(
    path: String,
): WitherFruit(
    path = path,
    rarity = Rarity.RARE,
    tooltipKey = ModItems.zoanTooltipKey
) {
    enum class Form { Base, Full, Hybrid }
    abstract var currentForm: Form
    abstract fun baseTransform(user: LivingEntity)
    abstract fun transform(user: LivingEntity)
    abstract fun hybridTransform(user: LivingEntity)
}