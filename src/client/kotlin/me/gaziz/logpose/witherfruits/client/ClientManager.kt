package me.gaziz.logpose.witherfruits.client

import me.gaziz.logpose.witherfruits.Initializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text

object ClientManager {
    var canSwim: Boolean = true
    private var isPressed = false
    fun initialize() {
        val firstAbilityKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Ability 1",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                Text.translatable(Initializer.MOD_NAME_ID.toTranslationKey()).string
            )
        )
        ClientTickEvents.END_CLIENT_TICK.register {
            if(
                firstAbilityKey.isPressed
            ) {
                if(
                    !isPressed &&
                    !canSwim
                ) {
                    isPressed = true
                    NetworkManager.sendAbilityEventC2S(1)
                }
            } else {
                isPressed = false
            }
        }
    }
}