package me.gaziz.logpose.witherfruits.client

import me.gaziz.logpose.witherfruits.Initializer.MOD_NAME_ID
import me.gaziz.logpose.witherfruits.client.Initializer.canSwim
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text

object KeyBindManager {
    fun initialize() {
        val firstAbilityKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Ability 1",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_R,
                Text.translatable(MOD_NAME_ID.toTranslationKey()).string
            )
        )
        var firstPressed = false
        val secondAbilityKey = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Ability 2",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_G,
                Text.translatable(MOD_NAME_ID.toTranslationKey()).string
            )
        )
        var secondPressed = false
        ClientTickEvents.END_CLIENT_TICK.register {
            if(it?.player?.isTouchingWater == false) {
                if(
                    firstAbilityKey.isPressed
                ) {
                    if(!firstPressed && !canSwim) {
                        firstPressed = true
                        NetworkManager.sendAbilityEventC2S(1)
                    }
                } else { firstPressed = false }
                if(
                    secondAbilityKey.isPressed
                ) {
                    if(!secondPressed && !canSwim) {
                        secondPressed = true
                        NetworkManager.sendAbilityEventC2S(2)
                    }
                } else { secondPressed = false }
            }
        }
    }
}