package me.gaziz.logpose.witherfruits.fruit.zoan

import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Rarity

abstract class ZoanFruit(
    path: String,
): WitherFruit(
    path = path,
    rarity = Rarity.RARE,
    tooltipKey = ModFruits.zoanTooltipKey
) {
    enum class Form { Base, Full, Hybrid }
    abstract fun toggleTransform(player: ServerPlayerEntity)
    abstract fun toggleHybridForm(player: ServerPlayerEntity)
}