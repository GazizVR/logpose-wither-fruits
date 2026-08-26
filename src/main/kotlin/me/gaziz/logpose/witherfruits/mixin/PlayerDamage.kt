package me.gaziz.logpose.witherfruits.mixin

import me.gaziz.logpose.witherfruits.PlayerManager
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.player.PlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

@Mixin(PlayerEntity::class)
class PlayerDamage {
    @Inject(
        method = ["damage"],
        at = [At(value = "HEAD")],
        cancellable = true
    )
    fun onDamage(
        damageSource: DamageSource,
        amount: Float,
        cir: CallbackInfoReturnable<Boolean>
    ){
        this as? PlayerEntity ?: return
        val damageTypes = PlayerManager.getDamageTypes(uuidAsString) ?: return
        damageTypes.forEach {
            if(damageSource.isIn(it)) {
                cir.returnValue = false
            }
        }
    }
}