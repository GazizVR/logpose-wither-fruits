package me.gaziz.logpose.witherfruits.item

import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Rarity

class ZoanFruit(
    path: String,
): WitherFruit(
    path = path,
    rarity = Rarity.RARE,
    tooltipKey = ModItems.zoanTooltipKey
) {
    private var isTransform = false
    fun toggleTransform(
        player: ServerPlayerEntity,
    ) {
        isTransform = !isTransform
        if(isTransform) {
            player.sendMessage(Text.literal("transformation"))
        } else {
            player.sendMessage(Text.literal("inverse transformation"))
        }
    }
}