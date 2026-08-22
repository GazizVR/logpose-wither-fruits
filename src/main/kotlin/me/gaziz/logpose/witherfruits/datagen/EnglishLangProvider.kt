package me.gaziz.logpose.witherfruits.datagen

import me.gaziz.logpose.witherfruits.item.LogiaFruit
import me.gaziz.logpose.witherfruits.item.ModItems
import me.gaziz.logpose.witherfruits.item.ParameciaFruit
import me.gaziz.logpose.witherfruits.item.ZoanFruit
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
            ModItems.ITEM_GROUP_KEY,
            "LogPose: Wither Fruits"
        )

        translationBuilder.add(
            ParameciaFruit().tooltipKey,
            "Type: Paramecia"
        )
        translationBuilder.add(
            ZoanFruit().tooltipKey,
            "Type: Zoan"
        )
        translationBuilder.add(
            LogiaFruit().tooltipKey,
            "Type: Logia"
        )

        translationBuilder.add(
            ModItems.rubberFruit.id.toTranslationKey("item"),
            "Gum-Gum Fruit"
        )
        translationBuilder.add(
            ModItems.catLeopardFruit.id.toTranslationKey("item"),
            "Cat-Cat, Leopard Fruit"
        )
        translationBuilder.add(
            ModItems.fireFruit.id.toTranslationKey("item"),
            "Flame-Flame Fruit"
        )

    }
}