package com.theendercore.glimmer_research

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import com.theendercore.glimmer_research.config.GlimmerResearchConfig

@Suppress("unused")
object GlimmerResearch {
    const val MODID = "glimmer_research"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(GlimmerResearch::class.simpleName)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::GlimmerResearchConfig)

    fun init() {
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)
}
