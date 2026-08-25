package me.gaziz.logpose.witherfruits.client.mixin

import me.gaziz.logpose.witherfruits.client.Initializer
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.PlayerEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(PlayerEntityRenderer::class)
abstract class PlayerRenderer {
    private var entityCopy: LivingEntity? = null
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
        if(Initializer.entityType == null) return
        if(
            entityCopy?.type != Initializer.entityType ||
            entityCopy == null ||
            entityCopy?.world == null
        ) {
            val world = player.world
            entityCopy = Initializer.entityType?.create(world) as LivingEntity?
        }
        entityCopy?.let { entity ->
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
        }
        ci.cancel()
    }
}