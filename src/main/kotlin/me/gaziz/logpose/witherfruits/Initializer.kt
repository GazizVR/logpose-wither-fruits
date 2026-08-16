package me.gaziz.logpose.witherfruits

import com.mojang.authlib.minecraft.client.MinecraftClient
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Initializer : ModInitializer {
	const val MOD_ID: String = "logpose-wither-fruits"

	fun id(path: String): Identifier = Identifier.of(MOD_ID, path)

	override fun onInitialize() {
		ModItems.initialize()
	}
}
