package com.theendercore.glimmer_research.data.gen.prov.data.tag

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import com.theendercore.glimmer_research.init.GRBlocks
import java.util.concurrent.CompletableFuture

class BlockTagsProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun addTags(lookup: HolderLookup.Provider) {
//        getOrCreateTagBuilder(BlockTags.STAIRS).add(GRBlocks.HOLYSTONE_STAIRS)
//        getOrCreateTagBuilder(BlockTags.SLABS).add(GRBlocks.HOLYSTONE_SLAB)
//        getOrCreateTagBuilder(BlockTags.WALLS).add(GRBlocks.HOLYSTONE_WALL)

//        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(GRBlocks.HOLYSTONE)
    }
}