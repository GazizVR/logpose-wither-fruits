package me.gaziz.logpose.witherfruits.fruit

import me.gaziz.logpose.witherfruits.Initializer
import me.gaziz.logpose.witherfruits.fruit.logia.LogiaFruit
import me.gaziz.logpose.witherfruits.fruit.paramecia.ParameciaFruit
import me.gaziz.logpose.witherfruits.fruit.zoan.CatLeopardFruit
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

object ModFruits {
    private val itemGroupKey = RegistryKey.of(
        RegistryKeys.ITEM_GROUP,
        Initializer.MOD_NAME_ID
    )
    private val itemGroup = FabricItemGroup.builder()
        .icon { ItemStack(rubberFruit) }
        .displayName(Text.translatable(Initializer.MOD_NAME_ID.toTranslationKey()))
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

    private fun tooltipKey(key: String) = Initializer
        .id(key)
        .toTranslationKey("tooltip")

    val parameciaTooltipKey: String = tooltipKey("type_paramecia")
    val logiaTooltipKey: String = tooltipKey("type_logia")
    val zoanTooltipKey: String = tooltipKey("type_zoan")

    val rubberFruit = ParameciaFruit("rubber_fruit")
    val catLeopardFruit = CatLeopardFruit()
    val flameFruit = LogiaFruit("flame_fruit")

    fun initialize() {
        Registry.register(
            Registries.ITEM_GROUP,
            itemGroupKey,
            itemGroup
        )
        register(rubberFruit.id, rubberFruit)
        register(catLeopardFruit.id, catLeopardFruit)
        register(flameFruit.id, flameFruit)
    }
}