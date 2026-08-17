package me.gaziz.logpose.witherfruits.client

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil

object ZoomManager {
    private const val MIN_FOV = 5
    private var defaultFov = 70
    var isZoomed = false
        private set
    private var forceZoom = false
    var zoomedFov = 30
        set(value) {
            if(value >= MIN_FOV){
                forceZoom = true
                field = value
            }
        }
    fun initialize() {
        val keyBind = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "Zoom Key",
                InputUtil.Type.KEYSYM,
                InputUtil.GLFW_KEY_Z,
                "Zoom"
            )
        )
        ClientTickEvents.END_CLIENT_TICK.register { client ->
            if(client.options != null) {
                if(client.options.fov.value != zoomedFov) {
                    defaultFov = client.options.fov.value
                }
                if(keyBind.isPressed) {
                    if(!isZoomed || forceZoom) {
                        isZoomed = true
                        forceZoom = true
                        client.options.fov.value = zoomedFov
                    }
                } else {
                    isZoomed = false
                    client.options.fov.value = defaultFov
                }
            }
        }
    }
}