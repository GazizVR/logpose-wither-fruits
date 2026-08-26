package me.gaziz.logpose.witherfruits.network

import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.entity.EntityType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload
import kotlin.jvm.optionals.getOrNull

data class EntityTypePayload(
    val uuidStr: String,
    val value: EntityType<*>?,
): CustomPayload {
    companion object {
        val ID: CustomPayload.Id<EntityTypePayload> = CustomPayload.id(
            "${Initializer.MOD_ID}.player_model"
        )
        private val entityTypeCodec = PacketCodecs.STRING.xmap(
            { if(it.contains("null")) null else EntityType.get(it).getOrNull() },
            { if(it == null) "null" else EntityType.getId(it).toString() }
        )
        val CODEC: PacketCodec<RegistryByteBuf, EntityTypePayload> = PacketCodec.tuple(
            PacketCodecs.STRING, EntityTypePayload::uuidStr,
            entityTypeCodec, EntityTypePayload::value,
            ::EntityTypePayload
        )
    }
    override fun getId(): CustomPayload.Id<EntityTypePayload> {
        return ID
    }
}
