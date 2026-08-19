package me.gaziz.logpose.witherfruits.item

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.util.Rarity

sealed class WitherFruitType(
    val rarity: Rarity,
    val tooltipId: String
) {
    object Paramecia : WitherFruitType(
        rarity = Rarity.UNCOMMON,
        tooltipId = Initializer.id("type_paramecia").toTranslationKey("tooltip")
    )
    object Zoan: WitherFruitType(
        rarity = Rarity.RARE,
        tooltipId = Initializer.id("type_zoan").toTranslationKey("tooltip")
    )
    object Logia: WitherFruitType(
        rarity = Rarity.EPIC,
        tooltipId = Initializer.id("type_logia").toTranslationKey("tooltip")
    )
}