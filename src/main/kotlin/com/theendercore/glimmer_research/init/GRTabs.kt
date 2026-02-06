package com.theendercore.glimmer_research.init

import com.theendercore.glimmer_research.GlimmerResearch.MODID
import com.theendercore.glimmer_research.GlimmerResearch.id
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item


object GRTabs {
    var TAB_NAME = "itemGroup.$MODID.$MODID"

    @Suppress("unused")
    val TAB = register(
        MODID, FabricItemGroup.builder()
            .title(Component.translatable(TAB_NAME))
            .icon { GRBlocks.GLIMMER.asItem().defaultInstance }
            .displayItems { params, output ->
                output.acceptAll(GRItems.ITEMS.map(Item::getDefaultInstance))
            }

    )

    fun init() = Unit
    fun register(id: String, tab: CreativeModeTab.Builder): CreativeModeTab {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id(id), tab.build())
    }
}