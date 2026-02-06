package com.theendercore.glimmer_research.api

import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.world.item.Item

object CauldronInteractionRegistry {
    fun registerDefault(item: Item, interaction: CauldronInteraction) {
        for (entry in CauldronInteraction.INTERACTIONS) {
            entry.value.map()[item] = interaction
        }
    }
}