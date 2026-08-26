package me.gaziz.logpose.witherfruits.fruit.logia

import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.fruit.WitherFruit
import net.minecraft.util.Rarity

class LogiaFruit(path: String): WitherFruit(
    path = path,
    rarity = Rarity.EPIC,
    tooltipKey = ModFruits.logiaTooltipKey
)