package com.theendercore.glimmer_research.util

import com.theendercore.glimmer_research.GlimmerResearch.MODID
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import kotlin.jvm.optionals.getOrNull

fun Holder<*>.getId() = unwrapKey().getOrNull()?.location()
fun <T> isModHolder(holder: Holder<T>) = holder.`is` { it.location().namespace == MODID }

fun <T> getModHolders(registry: Registry<T>): List<Holder<T>> = registry.holders()
    .filter(::isModHolder)
    .toList()

fun <T> getModEntries(registry: Registry<T>): List<T> = registry.holders()
    .filter(::isModHolder)
    .map(Holder<T>::value)
    .toList()

