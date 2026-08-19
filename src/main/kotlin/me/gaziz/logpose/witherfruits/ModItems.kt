package me.gaziz.logpose.witherfruits

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object ModItems {
    const val ITEM_GROUP_KEY = "itemgroup.${Initializer.MOD_ID}.wither_fruits"
    private val itemGroupKey = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        Initializer.id(ITEM_GROUP_KEY)
    )
    private val itemGroup = FabricItemGroup.builder()
        .icon { ItemStack(rubberFruit) }
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
        ItemGroupEvents
            .modifyEntriesEvent(itemGroupKey)
            .register { it.add { item } }
    }

    val tooltipParamecia: String = Initializer.id("type_paramecia").toTranslationKey("tooltip")
    val tooltipZoan: String = Initializer.id("type_zoan").toTranslationKey("tooltip")
    val tooltipLogia: String = Initializer.id("type_logia").toTranslationKey("tooltip")

    val rubberFruit = WitherFruit(
        "rubber_fruit",
        Text.translatable(tooltipParamecia)
    )
    val catLeopardFruit = WitherFruit(
        "cat_leopard_fruit",
        Text.translatable(tooltipZoan)
    )
    val fireFruit = WitherFruit(
        "fire_fruit",
        Text.translatable(tooltipLogia)
    )
    fun initialize() {
        Registry.register(
            Registries.ITEM_GROUP,
            itemGroupKey,
            itemGroup
        )
        register(rubberFruit.id, rubberFruit)
        register(catLeopardFruit.id, catLeopardFruit)
        register(fireFruit.id, fireFruit)
    }
}