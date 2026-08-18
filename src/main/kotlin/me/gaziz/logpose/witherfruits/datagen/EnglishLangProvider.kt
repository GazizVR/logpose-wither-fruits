package me.gaziz.logpose.witherfruits.datagen

import me.gaziz.logpose.witherfruits.ModItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class EnglishLangProvider(
    dataOutput: FabricDataOutput,
    registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>
) : FabricLanguageProvider(
    dataOutput,
    "en_us",
    registryLookup
) {
    override fun generateTranslations(
        registryLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: TranslationBuilder
    ) {
        translationBuilder.add(
            ModItems.rubberFruit.id.toTranslationKey("item"),
            "Rubber Fruit"
        )
        translationBuilder.add(
            ModItems.fireFruit.id.toTranslationKey("item"),
            "Fire Fruit"
        )
        translationBuilder.add(
            ModItems.ITEM_GROUP_KEY,
            "LogPose: Wither Fruits"
        )
    }
}