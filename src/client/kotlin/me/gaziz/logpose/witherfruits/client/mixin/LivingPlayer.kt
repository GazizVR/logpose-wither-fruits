package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.ClientManager
import net.minecraft.client.network.ClientPlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ClientPlayerEntity::class)
class LivingPlayer {
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
    @Inject(
        method = ["tickMovement"],
        at = [At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/input/Input;tick(ZF)V",
            shift = At.Shift.AFTER
        )]
    )
    fun onAfterInputTick(ci: CallbackInfo) {
        val player = this as? ClientPlayerEntity ?: return

        if (!ClientManager.canSwim && ClientManager.isTouchingWater) {
            player.isSprinting = false

            player.input.movementForward = 0.0f
            player.input.movementSideways = 0.0f

            player.input.jumping = false
            player.input.sneaking = false
        }
    }
}