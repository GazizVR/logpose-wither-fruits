package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.util.Rarity

class ParameciaFruit(path: String = ""): WitherFruit(
    path = path,
    rarity = Rarity.UNCOMMON,
    tooltipKey = Initializer.id("type_paramecia").toTranslationKey("tooltip")
) {

}