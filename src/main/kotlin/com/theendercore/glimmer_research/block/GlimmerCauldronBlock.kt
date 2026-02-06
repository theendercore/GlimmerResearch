package com.theendercore.glimmer_research.block

import com.mojang.serialization.MapCodec
import com.theendercore.glimmer_research.GlimmerResearch.sId
import com.theendercore.glimmer_research.api.CauldronInteractionRegistry
import com.theendercore.glimmer_research.init.GRBlocks
import com.theendercore.glimmer_research.util.givePlayer
import net.minecraft.core.BlockPos
import net.minecraft.core.cauldron.CauldronInteraction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractCauldronBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent

class GlimmerCauldronBlock(properties: Properties) : AbstractCauldronBlock(properties, GLIMMER) {
    public override fun codec(): MapCodec<GlimmerCauldronBlock> = CODEC
    override fun getContentHeight(blockState: BlockState): Double = 0.9375
    override fun isFull(blockState: BlockState): Boolean = true
    override fun entityInside(blockState: BlockState, level: Level, blockPos: BlockPos, entity: Entity) {
        if (isEntityInsideContent(blockState, blockPos, entity) && entity is ItemEntity) {
            entity.setDeltaMovement(0.0, 0.25, 0.0)
        }
    }

    override fun getAnalogOutputSignal(blockState: BlockState, level: Level, blockPos: BlockPos): Int = 12

    companion object {
        val CODEC: MapCodec<GlimmerCauldronBlock> = simpleCodec(::GlimmerCauldronBlock)
        val GLIMMER: CauldronInteraction.InteractionMap = CauldronInteraction.newInteractionMap(sId("glimmer"))

        fun bootstrapInteractions() {
            CauldronInteractionRegistry.registerDefault(Items.DIAMOND_BLOCK) { _, level, pos, player, hand, stack ->
                if (!level.isClientSide) {
                    if (!player.isCreative) player.getItemInHand(hand).shrink(1)
                    player.awardStat(Stats.FILL_CAULDRON)
                    player.awardStat(Stats.ITEM_USED.get(stack.item))
                    level.setBlockAndUpdate(pos, GRBlocks.GLIMMER_CAULDRON.defaultBlockState())
                    level.playSound(null, pos, SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.BLOCKS, 1.0F, 1.0F)
                    level.gameEvent(null, GameEvent.FLUID_PLACE, pos)
                }
                ItemInteractionResult.sidedSuccess(level.isClientSide)
            }

            val glimmerMap = GLIMMER.map()
            glimmerMap[Items.BUCKET] =
                CauldronInteraction { _, level, pos, player, _, bucketStack ->
                    if (!level.isClientSide) {
                        if (!(player.isCreative && player.inventory.contains { it.item == Items.DIAMOND_BLOCK }))
                            player.givePlayer(ItemStack(Items.DIAMOND_BLOCK))
                        player.awardStat(Stats.USE_CAULDRON)
                        player.awardStat(Stats.ITEM_USED.get(bucketStack.item))
                        level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState())
                        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F)
                        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos)
                    }

                    ItemInteractionResult.sidedSuccess(level.isClientSide)
                }
        }
    }
}
