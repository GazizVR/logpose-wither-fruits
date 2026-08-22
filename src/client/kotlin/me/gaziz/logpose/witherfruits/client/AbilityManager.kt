package me.gaziz.logpose.witherfruits.client

import me.gaziz.logpose.witherfruits.item.ModItems
import me.gaziz.logpose.witherfruits.network.AbilityEventPayload
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text

object AbilityManager {
    fun initialize() {
        val firstKeyBind = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Ability 1",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                Text.translatable(ModItems.ITEM_GROUP_KEY).string
            )
        )
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if(firstKeyBind.isPressed) {
                client.server?.let {
                    val payload = AbilityEventPayload(1)
                    ClientPlayNetworking.send(payload)
                }
            }
        }
    }
}