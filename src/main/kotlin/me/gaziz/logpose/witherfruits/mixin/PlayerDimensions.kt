package me.gaziz.logpose.witherfruits.mixin

import me.gaziz.logpose.witherfruits.PlayerManager
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(LivingEntity::class)
class PlayerDimensions {
    @Inject(
        method = ["getDimensions"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onGetDimensions(
        cir: CallbackInfoReturnable<EntityDimensions>
    ) {
        val player = this as? PlayerEntity
        if(player != null) {
            PlayerManager.getDimension(player.uuidAsString)?.let {
                cir.returnValue = it
                cir.cancel()
            }
        }
    }
}