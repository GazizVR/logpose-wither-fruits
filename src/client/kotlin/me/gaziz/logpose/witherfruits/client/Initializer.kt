package me.gaziz.logpose.witherfruits.client

import net.fabricmc.api.ClientModInitializer

object Initializer : ClientModInitializer {
    var canSwim: Boolean = true
	override fun onInitializeClient() {
        NetworkManager.initialize()
        KeyBindManager.initialize()
    }
}