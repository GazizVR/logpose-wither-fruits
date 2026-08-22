package me.gaziz.logpose.witherfruits.network

import io.netty.buffer.ByteBuf
import me.gaziz.logpose.witherfruits.Initializer
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.codec.PacketCodecs
import net.minecraft.network.packet.CustomPayload

data class AbilityEventPayload(val abilityNumber: Int): CustomPayload {
    companion object {
        val ID: CustomPayload.Id<CustomPayload> = CustomPayload
            .id("${Initializer.MOD_ID}.ability_event")
        val CODEC: PacketCodec<ByteBuf, AbilityEventPayload> = PacketCodecs.VAR_INT.xmap(
            ::AbilityEventPayload,
            AbilityEventPayload::abilityNumber
        )
    }
    override fun getId(): CustomPayload.Id<CustomPayload> {
        return ID
    }
}
