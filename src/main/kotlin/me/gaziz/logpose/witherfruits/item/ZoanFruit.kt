package me.gaziz.logpose.witherfruits.item

import net.minecraft.server.network.ServerPlayerEntity
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
    abstract fun toggleTransform(player: ServerPlayerEntity)
    abstract fun toggleHybridForm(player: ServerPlayerEntity)
}