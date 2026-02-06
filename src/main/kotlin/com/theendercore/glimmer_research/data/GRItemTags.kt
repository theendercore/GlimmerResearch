package com.theendercore.glimmer_research.data

import com.theendercore.glimmer_research.GlimmerResearch.id
import net.minecraft.core.registries.Registries
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

object GRItemTags {
    var TAGS = mutableListOf<TagKey<Item>>()

//    var HOLIELAMB_SACRIFICE = key("holielamb_sacrifice")

    fun key(name: String): TagKey<Item> {
        val tag = TagKey.create(Registries.ITEM, id(name))
        TAGS.add(tag)
        tag.translationKey
        return  tag
    }
}