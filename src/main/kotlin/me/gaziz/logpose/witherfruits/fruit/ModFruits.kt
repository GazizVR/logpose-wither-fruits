package me.gaziz.logpose.witherfruits.fruit

import me.gaziz.logpose.witherfruits.Initializer
import me.gaziz.logpose.witherfruits.fruit.logia.FlameFruit
import me.gaziz.logpose.witherfruits.fruit.paramecia.GumFruit
import me.gaziz.logpose.witherfruits.fruit.zoan.CatLeopardFruit
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.loot.v3.LootTableEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTables
import net.minecraft.loot.condition.RandomChanceLootCondition
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
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
        .icon { ItemStack(gumFruit) }
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

    val gumFruit = GumFruit()
    val catLeopardFruit = CatLeopardFruit()
    val flameFruit = FlameFruit()

    fun initialize() {
        Registry.register(
            Registries.ITEM_GROUP,
            itemGroupKey,
            itemGroup
        )
        register(gumFruit.id, gumFruit)
        register(catLeopardFruit.id, catLeopardFruit)
        register(flameFruit.id, flameFruit)
        LootTableEvents.MODIFY.register { key, builder, source, lookup ->
            if(key == LootTables.BURIED_TREASURE_CHEST) {
                val poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1f))
                    .conditionally(RandomChanceLootCondition.builder(0.25f))
                    .with(ItemEntry.builder(gumFruit).weight(1))
                    .with(ItemEntry.builder(flameFruit).weight(2))
                    .with(ItemEntry.builder(catLeopardFruit).weight(3))
                builder.pool(poolBuilder)
            }
            if(key == LootTables.SHIPWRECK_TREASURE_CHEST) {
                val poolBuilder = LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1f))
                    .conditionally(RandomChanceLootCondition.builder(0.1f))
                    .with(ItemEntry.builder(gumFruit).weight(1))
                    .with(ItemEntry.builder(flameFruit).weight(2))
                    .with(ItemEntry.builder(catLeopardFruit).weight(3))
                builder.pool(poolBuilder)
            }
        }
    }
}