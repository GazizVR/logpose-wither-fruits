package me.gaziz.logpose.witherfruits.network

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.item.ModItems
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
                PersistFruitsState()
                    .getPersistFruitsState(ctx.server())
                    .getFruits()[ctx.player().uuidAsString]
                    ?.let {
                        if(it == ModItems.catLeopardFruit) {
                            when {
                                payload.abilityNumber == 1 -> {
                                    ModItems.catLeopardFruit.toggleTransform(ctx.player())
                                }
                            }
                        } else {
                            LoggerFactory
                                .getLogger(NetworkManager::class.java)
                                .info("${ctx.player().name.string} ${payload.abilityNumber}")
                        }
                    }
            }
        }
    }
}