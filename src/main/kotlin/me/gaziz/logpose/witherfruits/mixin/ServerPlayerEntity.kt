package me.gaziz.logpose.witherfruits.mixin

import me.gaziz.logpose.witherfruits.UsersManager
import net.minecraft.server.network.ServerPlayerEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(ServerPlayerEntity::class)
abstract class ServerPlayerEntity {
    @Accessor("isTouchingWater")
    abstract fun isTouchingWater(): Boolean
    @Accessor("getUuidAsString")
    abstract fun getUuid(): String
    @Inject(
        method = ["updateInput"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onUpdateInput(
        ci: CallbackInfo
    ) {
        if(
            isTouchingWater() &&
            UsersManager.fruits != null
        ) {
            ci.cancel()
        }
    }
}