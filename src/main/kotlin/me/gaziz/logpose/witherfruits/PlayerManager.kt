package me.gaziz.logpose.witherfruits

import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityPose
import net.minecraft.entity.player.PlayerEntity

object PlayerManager {
    private val dimensions = mutableMapOf<String, EntityDimensions>()
    fun getDimension(key: String): EntityDimensions? = dimensions[key]
    fun setDimension(
        player: PlayerEntity,
        value: EntityDimensions
    ) {
        dimensions[player.uuidAsString] = value
        player.pose = EntityPose.ROARING
    }
    fun removeDimension(player: PlayerEntity) {
        dimensions.remove(player.uuidAsString)
        player.pose = EntityPose.ROARING
    }
}