package com.theendercore.glimmer_research.init

import com.theendercore.glimmer_research.GlimmerResearch.id
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType


object GREntities {

   /* val HOLIELAMB = register(
        "holielamb", EntityType.Builder.of(::Sheep, MobCategory.CREATURE)
            .sized(0.9F, 1.3F)
            .eyeHeight(1.235F)
            .passengerAttachments(1.2375F)
            .clientTrackingRange(10)
    )*/

    fun init() {
//        FabricDefaultAttributeRegistry.register(HOLIELAMB, Sheep.createAttributes())
    }

    fun <T : Entity> register(id: String, builder: EntityType.Builder<T>): EntityType<T> {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, id(id), builder.build(id(id).toString()))
    }
}