package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.item.ModItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.minecraft.network.RegistryByteBuf
import net.minecraft.network.codec.PacketCodec
import net.minecraft.network.packet.CustomPayload
import net.minecraft.util.Identifier

object Initializer : ModInitializer {
	const val MOD_ID: String = "logpose-wither-fruits"
	fun id(path: String): Identifier = Identifier.of(MOD_ID, path)
	override fun onInitialize() {
		PayloadTypeRegistry.playS2C().register(
			CanSwimPayload.ID,
			CanSwimPayload.CODEC as PacketCodec<RegistryByteBuf, CustomPayload>
		)
		ModItems.initialize()
		UsersManager.initialize()
	}
}
