package me.gaziz.logpose.witherfruits.datagen

import me.gaziz.logpose.witherfruits.Initializer
import me.gaziz.logpose.witherfruits.fruit.ModFruits
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
            Initializer.MOD_NAME_ID,
            "LogPose: Wither Fruits"
        )

        translationBuilder.add(
            ModFruits.parameciaTooltipKey,
            "Type: Paramecia"
        )
        translationBuilder.add(
            ModFruits.zoanTooltipKey,
            "Type: Zoan"
        )
        translationBuilder.add(
            ModFruits.logiaTooltipKey,
            "Type: Logia"
        )

        translationBuilder.add(
            ModFruits.rubberFruit.id.toTranslationKey("item"),
            "Gum-Gum Fruit"
        )
        translationBuilder.add(
            ModFruits.catLeopardFruit.id.toTranslationKey("item"),
            "Cat-Cat, Leopard Fruit"
        )
        translationBuilder.add(
            ModFruits.flameFruit.id.toTranslationKey("item"),
            "Flame-Flame Fruit"
        )

    }
}