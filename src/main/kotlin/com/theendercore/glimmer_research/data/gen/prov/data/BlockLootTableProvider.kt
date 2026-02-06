package com.theendercore.glimmer_research.data.gen.prov.data

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.core.HolderLookup
import net.minecraft.world.level.block.SlabBlock
import com.theendercore.glimmer_research.init.GRBlocks
import java.util.concurrent.CompletableFuture

class BlockLootTableProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) :
    FabricBlockLootTableProvider(o, r) {
    override fun generate() {

        for (block in GRBlocks.BLOCKS) {
            when (block) {
                is SlabBlock -> add(block, ::createSlabItemTable)
                else -> dropSelf(block)
            }
        }
    }

}