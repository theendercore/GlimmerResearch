package com.theendercore.glimmer_research.data

import com.theendercore.glimmer_research.GlimmerResearch.id
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block

object GRBlockTags {
//    var HOLIELAMB_SACRIFICE = key("holielamb_sacrifice")

    fun key(name: String): TagKey<Block> = TagKey.create(Registries.BLOCK, id(name))
}