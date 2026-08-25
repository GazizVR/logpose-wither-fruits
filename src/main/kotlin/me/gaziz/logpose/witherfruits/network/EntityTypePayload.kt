package me.gaziz.logpose.witherfruits.network

import io.netty.buffer.ByteBuf
import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.entity.EntityType
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

data class EntityTypePayload(
    val value: EntityType<*>?,
): CustomPayload {
    companion object {
        val ID: CustomPayload.Id<EntityTypePayload> = CustomPayload.id(
            "${Initializer.MOD_ID}.player_model"
        )
        val CODEC: PacketCodec<ByteBuf, EntityTypePayload> = PacketCodecs.STRING.xmap(
            { EntityTypePayload(EntityType.get(it).orElse(null)) },
            { EntityType.getId(it.value).toString() }
        )
    }
    override fun getId(): CustomPayload.Id<EntityTypePayload> {
        return ID
    }
}
