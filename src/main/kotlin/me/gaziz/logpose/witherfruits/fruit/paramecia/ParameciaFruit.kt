package me.gaziz.logpose.witherfruits.fruit.paramecia

import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Rarity

abstract class ParameciaFruit(path: String): WitherFruit(
    path = path,
    rarity = Rarity.UNCOMMON,
    tooltipKey = ModFruits.parameciaTooltipKey
) {
    override fun onRemove(user: ServerPlayerEntity) {}
}