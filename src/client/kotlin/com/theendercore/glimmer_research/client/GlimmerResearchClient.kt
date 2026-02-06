package com.theendercore.glimmer_research.client

import com.theendercore.glimmer_research.init.GRBlocks
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block

@Suppress("unused")
object GlimmerResearchClient {
    fun init() {
//        EntityRendererRegistry.register(EverEntities.HOLIELAMB, ::SheepRenderer)
        layer(RenderType.translucent(), GRBlocks.GLIMMER_CAULDRON)
    }

    fun layer(type: RenderType, vararg blocks: Block) {
        BlockRenderLayerMap.INSTANCE.putBlocks(type, *blocks)
    }
}