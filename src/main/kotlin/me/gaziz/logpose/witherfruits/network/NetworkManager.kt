package me.gaziz.logpose.witherfruits.network

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import org.slf4j.LoggerFactory

object NetworkManager {
    fun sendCanSwimS2C(
        player: ServerPlayerEntity,
        canSwim: Boolean,
    ) {
        val payload = CanSwimPayload(canSwim)
        ServerPlayNetworking.send(player,payload)
    }
    fun initialize() {
        PayloadTypeRegistry.playS2C().register(
            CanSwimPayload.ID,
            CanSwimPayload.CODEC as PacketCodec<RegistryByteBuf, CustomPayload>
        )
        PayloadTypeRegistry.playC2S().register(
            AbilityEventPayload.ID,
            AbilityEventPayload.CODEC as PacketCodec<RegistryByteBuf, CustomPayload>
        )
        ServerPlayNetworking.registerGlobalReceiver (
            AbilityEventPayload.ID,
        ) { payload, ctx ->
            if(payload is AbilityEventPayload) {
                LoggerFactory
                    .getLogger(NetworkManager::class.java)
                    .info("${ctx.player().name.string} ${payload.abilityNumber}")
            }
        }
    }
}