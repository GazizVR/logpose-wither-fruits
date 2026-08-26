package me.gaziz.logpose.witherfruits.client

import net.fabricmc.api.ClientModInitializer
import net.minecraft.entity.LivingEntity

object Initializer : ClientModInitializer {
    var canSwim: Boolean = true
    var entityCopies = mutableMapOf<String, LivingEntity>()
	override fun onInitializeClient() {
        NetworkManager.initialize()
        KeyBindManager.initialize()
    }
}