package me.gaziz.logpose.witherfruits

import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

data class CanSwimPayload(val value: Boolean): CustomPayload {
    companion object {
        val ID: CustomPayload.Id<CustomPayload> = CustomPayload
            .id("${Initializer.MOD_ID}.can_swim")
        val CODEC: PacketCodec<ByteBuf, CanSwimPayload> = PacketCodecs
            .BOOL
            .xmap(
                ::CanSwimPayload,
                CanSwimPayload::value
            )
    }
    override fun getId(): CustomPayload.Id<CustomPayload> {
        return ID
    }
}