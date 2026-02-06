package com.theendercore.glimmer_research.init

import com.theendercore.glimmer_research.GlimmerResearch.id
import com.theendercore.glimmer_research.util.getModEntries
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties


object GRItems {
    val ITEMS get() = getModEntries(BuiltInRegistries.ITEM)

    val DB_GLIMMER = register("db_glimmer", Item(Properties()))

    fun init() = Unit
    fun register(id: String, item: Item): Item = Registry.register(BuiltInRegistries.ITEM, id(id), item)
}