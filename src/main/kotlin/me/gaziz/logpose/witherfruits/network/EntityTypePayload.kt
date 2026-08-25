package me.gaziz.logpose.witherfruits.network

import io.netty.buffer.ByteBuf
import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.entity.EntityType
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import kotlin.jvm.optionals.getOrNull

data class EntityTypePayload(
    val value: EntityType<*>?,
): CustomPayload {
    companion object {
        val ID: CustomPayload.Id<EntityTypePayload> = CustomPayload.id(
            "${Initializer.MOD_ID}.player_model"
        )
        val CODEC: PacketCodec<ByteBuf, EntityTypePayload> = PacketCodecs.STRING.xmap(
            {
                val newValue = if(it.contains("null")) null else EntityType.get(it).getOrNull()
                EntityTypePayload(newValue)
            },
            {
                if(it.value == null) "null" else EntityType.getId(it.value).toString()
            }
        )
    }
    override fun getId(): CustomPayload.Id<EntityTypePayload> {
        return ID
    }
}
