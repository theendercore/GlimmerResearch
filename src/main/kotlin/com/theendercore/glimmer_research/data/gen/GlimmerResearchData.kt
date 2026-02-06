package com.theendercore.glimmer_research.data.gen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.core.RegistrySetBuilder
import com.theendercore.glimmer_research.data.gen.prov.assets.EnLangProvider
import com.theendercore.glimmer_research.data.gen.prov.assets.ModelProvider
import com.theendercore.glimmer_research.data.gen.prov.data.BlockLootTableProvider
import com.theendercore.glimmer_research.data.gen.prov.data.RecipeProvider
import com.theendercore.glimmer_research.data.gen.prov.data.tag.BlockTagsProvider
import com.theendercore.glimmer_research.data.gen.prov.data.tag.EntityTypeTagsProvider
import com.theendercore.glimmer_research.data.gen.prov.data.tag.ItemTagsProvider

@Suppress("unused")
object GlimmerResearchData : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()

        pack.addProvider(::ModelProvider)
        pack.addProvider(::EnLangProvider)
        // Data
        pack.addProvider(::BlockLootTableProvider)
        val bt = pack.addProvider(::BlockTagsProvider)
        pack.addProvider { o, r -> ItemTagsProvider(o, r, bt) }
        pack.addProvider(::EntityTypeTagsProvider)
        pack.addProvider(::RecipeProvider)
    }

    override fun buildRegistry(gen: RegistrySetBuilder) {
//        gen.add(RegistryKeys.BIOME, TemplateBiomes::boostrap)
    }
}
