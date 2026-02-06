package com.theendercore.glimmer_research

import com.theendercore.glimmer_research.config.GlimmerResearchConfig
import com.theendercore.glimmer_research.init.GRBlocks
import com.theendercore.glimmer_research.init.GREntities
import com.theendercore.glimmer_research.init.GRItems
import com.theendercore.glimmer_research.init.GRTabs
import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Suppress("unused")
object GlimmerResearch {
    const val MODID = "glimmer_research"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(GlimmerResearch::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::GlimmerResearchConfig)

    fun init() {
        GRItems.init()
        GRBlocks.init()
        GREntities.init()
        GRTabs.init()
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)
}
