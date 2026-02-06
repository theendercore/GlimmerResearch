package com.theendercore.glimmer_research.data.gen.prov.assets

import com.theendercore.glimmer_research.init.GRBlocks
import com.theendercore.glimmer_research.init.GRItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.ModelTemplates
import net.minecraft.world.item.Item

class ModelProvider(o: FabricDataOutput) : FabricModelProvider(o) {
    override fun generateBlockStateModels(gen: BlockModelGenerators) {
        gen.createTrivialCube(GRBlocks.GLIMMER_BLOCK)
    }

    override fun generateItemModels(gen: ItemModelGenerators) {
        gen.itemModels(ModelTemplates.FLAT_ITEM, GRItems.DB_GLIMMER)
    }

    fun ItemModelGenerators.itemModels(model: ModelTemplate, vararg faltItems: Item) {
        for (item in faltItems) generateFlatItem(item, model)
    }

}