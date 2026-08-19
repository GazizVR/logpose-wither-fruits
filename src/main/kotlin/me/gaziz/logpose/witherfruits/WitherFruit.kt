package me.gaziz.logpose.witherfruits

import me.gaziz.logpose.witherfruits.UsersManager.fruits
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.Rarity
import net.minecraft.world.World
import java.util.*

class WitherFruit(
    path: String,
    private val tooltipText: Text? = null
): Item(
    Settings()
        .maxCount(1)
        .rarity(Rarity.EPIC)
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
        if(tooltipText != null) {
            tooltip.add(tooltipText)
        }
    }

    override fun finishUsing(
        stack: ItemStack,
        world: World,
        user: LivingEntity
    ): ItemStack {
        if(!world.isClient) {
            val uuid = user.uuidAsString
            val fruit = fruits[uuid]
            if(fruit != null) {
                stack.decrement(1)
                user.health = 0.1f
                val statusEffect = StatusEffectInstance(
                    StatusEffects.WITHER,
                    StatusEffectInstance.INFINITE,
                    StatusEffectInstance.MAX_AMPLIFIER,
                    false,
                    false,
                    false
                )
                user.removeStatusEffect(StatusEffects.NAUSEA)
                user.addStatusEffect(statusEffect)
                UsersManager.removeFruit(uuid)
            } else {
                UsersManager.setFruit(uuid,this)
            }
        }
        val foodComponent = stack.get<FoodComponent?>(DataComponentTypes.FOOD)
        return if (foodComponent != null) user.eatFood(world, stack, foodComponent) else stack
    }
}