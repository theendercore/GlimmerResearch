@file:Suppress("unused")

package com.theendercore.glimmer_research.util

import com.theendercore.glimmer_research.GlimmerResearch.MODID
import net.minecraft.core.Holder
import net.minecraft.core.Registry
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
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

// Player
fun Player.msg(message: String) = sendSystemMessage(Component.translatable(message))
fun Player.info(message: String) = displayClientMessage(Component.translatable(message), true)

fun Player.givePlayer(stack: ItemStack) {
    if (!stack.isEmpty && !addItem(stack)) {
        val itemEntity = drop(stack, false)
        itemEntity?.setNoPickUpDelay()
    }
}
