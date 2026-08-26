package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.fruit.ModFruits
import me.gaziz.logpose.witherfruits.modifier.BuffManager
import me.gaziz.logpose.witherfruits.modifier.DebuffManager
import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object Initializer : ModInitializer {
	const val MOD_ID = "logpose-wither-fruits"
	val MOD_NAME_ID: Identifier = id("name").withPrefixedPath("text")
	fun id(path: String): Identifier = Identifier.of(MOD_ID, path)
	override fun onInitialize() {
		NetworkManager.initialize()
		ModFruits.initialize()
		PlayerManager.initialize()
		BuffManager.initialize()
		DebuffManager.initialize()
	}
}
