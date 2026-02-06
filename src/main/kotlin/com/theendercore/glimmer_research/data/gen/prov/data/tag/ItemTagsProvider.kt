package com.theendercore.glimmer_research.data.gen.prov.data.tag

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.core.HolderLookup
import java.util.concurrent.CompletableFuture

class ItemTagsProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>, bt: BlockTagsProvider) :
    FabricTagProvider.ItemTagProvider(o, r, bt) {
    override fun addTags(lookup: HolderLookup.Provider) {
//        copy(BlockTags.STAIRS, ItemTags.STAIRS)
//        copy(BlockTags.SLABS, ItemTags.SLABS)
//        copy(BlockTags.WALLS, ItemTags.WALLS)
    }
}