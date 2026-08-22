package me.gaziz.logpose.witherfruits.client

import net.fabricmc.api.ClientModInitializer

object Initializer : ClientModInitializer {
	override fun onInitializeClient() {
        ClientManager.initialize()
    }
}