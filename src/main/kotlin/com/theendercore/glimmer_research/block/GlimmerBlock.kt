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

        if (entity is ItemEntity) {
            val stack = entity.item

            val (count, list) = getRecipeInputs(level, stack.copy())
            if (list.isEmpty()) return

            val iterations = stack.count / count

            repeat(iterations) {
                for (itemStack in list) {
                    val resultItem = ItemEntity(
                        level, entity.x, entity.y, entity.z, itemStack.copy(),
                        level.random.nextDouble() * 0.1 - 0.05,
                        0.25,
                        level.random.nextDouble() * 0.1 - 0.05
                    )
                    level.addFreshEntity(resultItem)
                }
            }
            stack.shrink(count * iterations)
        }
    }
}

