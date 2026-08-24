package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.ClientManager
import net.minecraft.client.MinecraftClient
import net.minecraft.client.Mouse
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Mouse::class)
class MouseButton {
    @Shadow
    private var activeButton = 0
    @Shadow
    private var client: MinecraftClient? = null

    @Inject(
        method = ["onMouseButton"],
        at = [At("HEAD")],
        cancellable = true
    )
    private fun onMouseButton(cir: CallbackInfo) {
        if(
            !ClientManager.canSwim &&
            client?.currentScreen == null &&
            client?.player?.isTouchingWater == true
        ) {
            KeyBinding.setKeyPressed(
                InputUtil.Type.MOUSE.createFromCode(activeButton),
                false
            )
            cir.cancel()
        }
    }
}