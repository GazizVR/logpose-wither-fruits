package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.Initializer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.ModelPart
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.PlayerEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(PlayerEntityRenderer::class)
class PlayerRenderer {
    @Inject(
        method = ["renderArm"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onRenderArm(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        player: AbstractClientPlayerEntity,
        arm: ModelPart,
        sleeve: ModelPart,
        ci: CallbackInfo
    ) {
        if(Initializer.entityCopies[player.uuidAsString] != null) {
            ci.cancel()
        }
    }
    @Inject(
        method = ["render"],
        at = [At("HEAD")],
        cancellable = true
    )
    fun onRender(
        player: AbstractClientPlayerEntity,
        yaw: Float,
        tickDelta: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        light: Int,
        ci: CallbackInfo
    ) {
        val entityCopy = Initializer.entityCopies[player.uuidAsString] ?: return
        entityCopy.let { entity ->
            entity.copyPositionAndRotation(player)
            entity.bodyYaw = player.bodyYaw
            entity.headYaw = player.headYaw
            entity.prevBodyYaw = player.prevBodyYaw
            entity.prevHeadYaw = player.prevHeadYaw
            entity.age = player.age
            entity.limbAnimator.speed = player.limbAnimator.speed
            entity.pose = player.pose

            val dispatcher = MinecraftClient.getInstance().entityRenderDispatcher
            val renderer = dispatcher.getRenderer(entity)
            renderer.render(
                entity,
                yaw,
                tickDelta,
                matrixStack,
                vertexConsumerProvider,
                light
            )
            ci.cancel()
        }
    }
}