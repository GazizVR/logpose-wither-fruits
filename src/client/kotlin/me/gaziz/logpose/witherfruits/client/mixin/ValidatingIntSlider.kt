package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.ZoomManager
import net.minecraft.client.option.SimpleOption
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
import java.util.Optional

@Mixin(SimpleOption.ValidatingIntSliderCallbacks::class)
class ValidatingIntSlider {
    @Inject(
        method = ["(Ljava/lang/Integer;)Ljava/util/Optional;"],
        at = [At(value = "RETURN")],
        cancellable = true
    )
    fun onValidate(
        value: Integer,
        cir: CallbackInfoReturnable<Optional<Int>>
    ) {
        if(
            value.toInt() == ZoomManager.zoomedFov &&
            cir.returnValue == Optional.empty<Int>()
        ) {
            cir.returnValue = Optional.of(ZoomManager.zoomedFov)
        }
    }
}