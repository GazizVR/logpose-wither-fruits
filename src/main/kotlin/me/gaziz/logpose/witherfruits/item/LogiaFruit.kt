package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.util.Rarity

class LogiaFruit(path: String = ""): WitherFruit(
    path = path,
    rarity = Rarity.EPIC,
    tooltipKey = Initializer.id("type_logia").toTranslationKey("tooltip")
) {
}