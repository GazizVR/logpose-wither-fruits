package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.util.Rarity

class ZoanFruit(path: String = ""): WitherFruit(
    path = path,
    rarity = Rarity.RARE,
    tooltipKey = Initializer.id("type_zoan").toTranslationKey("tooltip")
) {

}