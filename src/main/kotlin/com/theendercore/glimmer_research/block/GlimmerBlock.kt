package com.theendercore.glimmer_research.block

import com.theendercore.glimmer_research.util.getRecipeInputs
import com.theendercore.glimmer_research.util.givePlayer
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class GlimmerBlock(properties: Properties) : Block(properties) {

    override fun useItemOn(
        stack: ItemStack, state: BlockState, level: Level,
        pos: BlockPos, player: Player, hand: InteractionHand, result: BlockHitResult,
    ): ItemInteractionResult {
        val result = super.useItemOn(stack, state, level, pos, player, hand, result)

        if (hand == InteractionHand.OFF_HAND) return result
        if (level.isClientSide || stack.isEmpty) return ItemInteractionResult.CONSUME

        val (count, list) = getRecipeInputs(level, stack.copy())
        if (list.isEmpty()) {
            return ItemInteractionResult.FAIL
        }

        val iterations = stack.count / count

        repeat(iterations) {
            for (itemStack in list) {
                player.givePlayer(itemStack.copy())
            }
        }
        stack.shrink(count * iterations)


        return ItemInteractionResult.SUCCESS
    }

    override fun stepOn(level: Level, blockPos: BlockPos, blockState: BlockState, entity: Entity) {
        super.stepOn(level, blockPos, blockState, entity)

        if (entity is ItemEntity && !level.isClientSide) {
            if (entity.owner == null && entity.age <= 100) return
            if (entity.owner != null && entity.age <= 20) return

            val stack = entity.item

            //TODO add stack merging
            val (count, list) = getRecipeInputs(level, stack.copy())
            if (list.isEmpty()) return
            val iterations = stack.count / count
            val mergedList = mergeList(list, iterations)

            for (itemStack in mergedList) {
                val resultItem = ItemEntity(
                    level, entity.x, entity.y, entity.z, itemStack.copy(),
                    level.random.nextDouble() * 0.1 - 0.05,
                    0.25,
                    level.random.nextDouble() * 0.1 - 0.05
                )
                level.addFreshEntity(resultItem)
            }
            stack.shrink(count * iterations)
        }
    }

    fun mergeList(stacks: List<ItemStack>, multiplier: Int = 1): List<ItemStack> {
        if (stacks.size == 1 && multiplier == 1) return stacks

        val newList = ArrayList<ItemStack>(9)

        loop@ for (stkStack in stacks) {
            repeat(multiplier) {
                val newStack = stkStack.copy()
                if (newStack.isStackable && !newList.isEmpty()) {
                    for (listStack in newList) {
                        if (listStack.count < listStack.maxStackSize
                            && ItemStack.isSameItemSameComponents(listStack, newStack)
                        ) {
                            val combined = listStack.count + newStack.count
                            val max = listStack.maxStackSize
                            if (combined <= max) {
                                newStack.count = 0
                                listStack.count = combined
                            } else if (listStack.count < max) {
                                newStack.shrink(max - newStack.count)
                                listStack.count = max
                            }
                        }
                        if (newStack.isEmpty) {
                            continue@loop
                        }
                    }
                }
                newList.add(newStack)
            }
        }

        return newList
    }
}

