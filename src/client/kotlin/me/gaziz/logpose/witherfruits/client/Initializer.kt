package me.gaziz.logpose.witherfruits.client

import net.fabricmc.api.ClientModInitializer
import net.minecraft.entity.EntityType

object Initializer : ClientModInitializer {
    var canSwim: Boolean = true
    var entityType: EntityType<*>? = null
	override fun onInitializeClient() {
        NetworkManager.initialize()
        KeyBindManager.initialize()
    }
}