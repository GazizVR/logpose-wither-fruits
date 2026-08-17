package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.ZoomManager
import net.minecraft.client.Mouse
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Mouse::class)
class MouseScroll {
    @Inject(
        method = ["onMouseScroll"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onMouseScroll(
        window: Long,
        horizontal: Double,
        vertical: Double,
        ci: CallbackInfo
    ) {
        if(ZoomManager.isZoomed) {
            ZoomManager.zoomedFov -= vertical.toInt()
            ci.cancel()
        }
    }
}