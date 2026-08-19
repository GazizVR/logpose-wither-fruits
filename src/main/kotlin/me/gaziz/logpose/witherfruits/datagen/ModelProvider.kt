package me.gaziz.logpose.witherfruits.datagen

import me.gaziz.logpose.witherfruits.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models

class ModelProvider(
    output: FabricDataOutput
): FabricModelProvider(output) {
    override fun generateBlockStateModels(
        blockStateModelGenerator: BlockStateModelGenerator
    ) {}

    override fun generateItemModels(
        itemModelGenerator: ItemModelGenerator
    ) {
        itemModelGenerator.register(ModItems.rubberFruit, Models.GENERATED)
        itemModelGenerator.register(ModItems.catLeopardFruit, Models.GENERATED)
        itemModelGenerator.register(ModItems.fireFruit, Models.GENERATED)
    }
}