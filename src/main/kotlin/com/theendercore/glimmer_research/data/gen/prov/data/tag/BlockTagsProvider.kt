package com.theendercore.glimmer_research.data.gen.prov.data.tag

import com.theendercore.glimmer_research.init.GRBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import net.minecraft.tags.BlockTags
import java.util.concurrent.CompletableFuture

class BlockTagsProvider(output: FabricDataOutput, registriesFuture: CompletableFuture<HolderLookup.Provider>) :
    FabricTagProvider.BlockTagProvider(output, registriesFuture) {
    override fun addTags(lookup: HolderLookup.Provider) {
        getOrCreateTagBuilder(BlockTags.CAULDRONS).add(GRBlocks.GLIMMER_CAULDRON)
//        getOrCreateTagBuilder(BlockTags.SLABS).add(GRBlocks.HOLYSTONE_SLAB)
//        getOrCreateTagBuilder(BlockTags.WALLS).add(GRBlocks.HOLYSTONE_WALL)

//        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(GRBlocks.HOLYSTONE)
    }
}