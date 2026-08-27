package me.gaziz.logpose.witherfruits.network

import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.fruit.logia.LogiaFruit
import me.gaziz.logpose.witherfruits.fruit.zoan.ZoanFruit
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.entity.EntityType
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

object NetworkManager {
    fun sendCanSwimS2C(
        player: ServerPlayerEntity,
        canSwim: Boolean,
    ) {
        val payload = CanSwimPayload(canSwim)
        ServerPlayNetworking.send(player,payload)
    }
    fun sendEntityTypeS2C(
        player: ServerPlayerEntity,
        uuidStr: String,
        entityType: EntityType<*>?,
    ) {
        val payload = EntityTypePayload(uuidStr,entityType)
        ServerPlayNetworking.send(player,payload)
    }
    fun initialize() {
        PayloadTypeRegistry.playS2C().register(
            CanSwimPayload.ID,
            CanSwimPayload.CODEC as PacketCodec<RegistryByteBuf, CustomPayload>
        )
        PayloadTypeRegistry.playS2C().register(
            EntityTypePayload.ID,
            EntityTypePayload.CODEC
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
                        when (it) {
                            is ZoanFruit -> {
                                when(payload.abilityNumber) {
                                    1 -> it.toggleTransform(ctx.player())
                                    2 -> it.toggleHybridForm(ctx.player())
                                }
                            }

                            is LogiaFruit -> {
                                when(payload.abilityNumber) {
                                    1 -> it.firstAbility(ctx.player())
                                    2 -> it.secondAbility(ctx.player())
                                }
                            }

                            else -> {
                                ctx.player().sendMessageToClient(
                                    Text.literal("Not implemented yet"),
                                    true
                                )
                            }
                        }
                    }
            }
        }
    }
}