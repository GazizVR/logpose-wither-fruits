package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.ClientManager
import net.minecraft.entity.LivingEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(LivingEntity::class)
class ClientPlayer {
    @Inject(
        method = ["swimUpward"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onSwimUpward(ci: CallbackInfo) {
        if(!ClientManager.canSwim) {
            ci.cancel()
        }
    }
}