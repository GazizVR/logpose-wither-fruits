package me.gaziz.logpose.witherfruits.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object DataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(
		fabricDataGenerator: FabricDataGenerator
	) {
		val pack = fabricDataGenerator.createPack()
		pack.addProvider(::EnglishLangProvider)
		pack.addProvider(::ModelProvider)
	}
}