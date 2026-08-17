package me.gaziz.logpose.witherfruits

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import java.util.Optional

object ModItems {
    const val ITEM_GROUP_KEY = "itemgroup.${Initializer.MOD_ID}.wither_fruits"
    private val itemGroupKey = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        Initializer.id(ITEM_GROUP_KEY)
    )
    private val itemGroup = FabricItemGroup.builder()
        .icon { ItemStack(rubberFruitItem) }
        .displayName(Text.translatable(ITEM_GROUP_KEY))
        .build()
    private fun register(
        itemId: Identifier,
        item: Item
    ) {
        Registry.register(
            Registries.ITEM,
            itemId,
            item
        )
        Registry.register(
            Registries.ITEM_GROUP,
            itemGroupKey,
            itemGroup
        )
        ItemGroupEvents
            .modifyEntriesEvent(itemGroupKey)
            .register { itemGroup ->
                itemGroup.add { item }
            }
    }
    private val rubberFruitSettings = Item
        .Settings()
        .maxCount(1)
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
    val rubberFruitItem = object : Item(rubberFruitSettings) {
        override fun appendTooltip(
            stack: ItemStack,
            context: TooltipContext,
            tooltip: MutableList<Text>,
            type: TooltipType?
        ) {
            tooltip.add(Text.literal("GOD OF SUN POWER").formatted(Formatting.GOLD))
        }
    }
    val rubberFruitId = Initializer.id("rubber_fruit")
    val rubberFruit = register(
        rubberFruitId,
        rubberFruitItem
    )
    fun initialize() {
        rubberFruit
    }
}