package me.gaziz.logpose.witherfruits.fruit.paramecia

import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import net.minecraft.util.Rarity

class ParameciaFruit(path: String): WitherFruit(
    path = path,
    rarity = Rarity.UNCOMMON,
    tooltipKey = ModFruits.parameciaTooltipKey
)