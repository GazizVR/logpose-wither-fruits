package me.gaziz.logpose.witherfruits.client

import me.gaziz.logpose.witherfruits.client.Initializer.canSwim
import me.gaziz.logpose.witherfruits.network.AbilityEventPayload
import me.gaziz.logpose.witherfruits.network.CanSwimPayload
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking

object NetworkManager {
    fun sendAbilityEventC2S(abilityNumber: Int) {
        val payload = AbilityEventPayload(abilityNumber)
        ClientPlayNetworking.send(payload)
    }
    fun initialize() {
        ClientPlayNetworking.registerGlobalReceiver(
            CanSwimPayload.ID
        ) { payload, _ ->
            if (payload is CanSwimPayload) {
                canSwim = payload.value
            }
        }
    }
}