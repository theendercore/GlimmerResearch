package com.theendercore.glimmer_research.init

import com.theendercore.glimmer_research.GlimmerResearch.id
import com.theendercore.glimmer_research.block.GlimmerBlock
import com.theendercore.glimmer_research.util.getModEntries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy


object GRBlocks {
    val BLOCKS get() = getModEntries(BuiltInRegistries.BLOCK)


    val GLIMMER_BLOCK = register("glimmer_block", Block(ofFullCopy(Blocks.AMETHYST_BLOCK)))

    fun init() = Unit

    fun register(id: String, block: Block): Block {
        val rBlock = registerNoItem(id, block)
        GRItems.register(id, BlockItem(rBlock, Item.Properties()))
        return rBlock
    }

    fun registerNoItem(id: String, block: Block): Block = Registry.register(BuiltInRegistries.BLOCK, id(id), block)
}