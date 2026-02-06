package com.theendercore.glimmer_research.data.gen.prov.assets

import com.theendercore.glimmer_research.data.GRItemTags
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import com.theendercore.glimmer_research.init.GRBlocks
import com.theendercore.glimmer_research.init.GRItems
import com.theendercore.glimmer_research.init.GRTabs
import java.util.concurrent.CompletableFuture

class EnLangProvider(o: FabricDataOutput, r: CompletableFuture<HolderLookup.Provider>) : FabricLanguageProvider(o, r) {

    override fun generateTranslations(lookup: HolderLookup.Provider, gen: TranslationBuilder) {
        GRItems.ITEMS.forEach { gen.add(it.descriptionId, genLang(it.id)) }
        GRBlocks.BLOCKS.forEach { silentTry { gen.add(it.descriptionId, genLang(it.id)) } }
        GRItemTags.TAGS.forEach { gen.add(it.translationKey, genLang(it.location)) }

        gen.add(GRTabs.TAB_NAME, "Glimmer Research")
    }

    private fun genLang(id: ResourceLocation): String =
        id.path.split("_").joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }

    val Item.id get() = BuiltInRegistries.ITEM.getKey(this)
    val Block.id get() = BuiltInRegistries.BLOCK.getKey(this)
    val EntityType<*>.id get() = BuiltInRegistries.ENTITY_TYPE.getKey(this)

    fun silentTry(fn: Runnable) {
        try {
            fn.run()
        } catch (_: Exception) {
        }
    }
}