package me.gaziz.logpose.witherfruits.fruit

import me.gaziz.logpose.witherfruits.Initializer
import me.gaziz.logpose.witherfruits.PersistFruitsState
import me.gaziz.logpose.witherfruits.network.NetworkManager
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.world.World
import java.util.*

abstract class WitherFruit(
    path: String,
    rarity: Rarity,
    val tooltipKey: String? = null
): Item(
    Settings()
        .maxCount(1)
        .rarity(rarity)
        .food(
            FoodComponent(
                6,
                14.4f,
                true,
                1.6f,
                Optional.empty(),
                listOf(
                    FoodComponent.StatusEffectEntry(
                        StatusEffectInstance(
                            StatusEffects.NAUSEA,
                            360,
                            255
                        ),
                        1f
                    )
                )
            )
        )
) {
    val id: Identifier = Initializer.id(path)

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        if(tooltipKey != null) {
            tooltip.add(Text.translatable(tooltipKey))
        }
    }
    protected open fun onEat(user: ServerPlayerEntity) {}
    protected abstract fun onRemove(user: ServerPlayerEntity)
    override fun finishUsing(
        stack: ItemStack,
        world: World,
        user: LivingEntity
    ): ItemStack {
        if(
            !world.isClient &&
            world.server != null &&
            user is ServerPlayerEntity
        ) {
            onEat(user)
            val state = PersistFruitsState().getPersistFruitsState(world.server!!)
            val key = user.uuidAsString
            val fruit = state.getFruits()[key]
            if(fruit != null) {
                stack.decrement(1)
                fruit.onRemove(user)
                if(user.abilities.creativeMode) {
                    user.kill()
                } else {
                    user.removeStatusEffect(StatusEffects.NAUSEA)
                    user.damage(user.world.damageSources.wither(),user.maxHealth)
                }
                state.removeFruit(key)
            } else {
                state.setFruit(key,this)
            }
            NetworkManager.sendCanSwimS2C(
                user,
                fruit != null,
            )
        }
        val foodComponent = stack.get<FoodComponent?>(DataComponentTypes.FOOD)
        return if (foodComponent != null) user.eatFood(world, stack, foodComponent) else stack
    }
}