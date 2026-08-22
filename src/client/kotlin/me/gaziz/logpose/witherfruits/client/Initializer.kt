package me.gaziz.logpose.witherfruits.client

import me.gaziz.logpose.witherfruits.network.CanSwimPayload
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object Initializer : ClientModInitializer {
	var canSwim: Boolean = true
	private set
	override fun onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(
			CanSwimPayload.ID
        ) { payload, _ ->
            if (payload is CanSwimPayload) {
                canSwim = payload.value
            }
        }
        AbilityManager.initialize()
    }
}