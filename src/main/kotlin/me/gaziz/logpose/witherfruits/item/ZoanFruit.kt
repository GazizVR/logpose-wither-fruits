package me.gaziz.logpose.witherfruits.item

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
    abstract fun baseTransform()
    abstract fun transform()
    abstract fun hybridTransform()
}