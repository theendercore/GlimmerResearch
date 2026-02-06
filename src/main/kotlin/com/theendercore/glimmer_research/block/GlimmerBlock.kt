package com.theendercore.glimmer_research.block

import com.theendercore.glimmer_research.util.givePlayer
import com.theendercore.glimmer_research.util.info
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
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

        val reg = level.registryAccess()
        val recipes = level.recipeManager.getAllRecipesFor(RecipeType.CRAFTING)
            .filter { it.value().getResultItem(reg).item === stack.item }
            .reversed()

        if (recipes.isEmpty()) {
            player.info("Failed to find Any recipes")
            return ItemInteractionResult.CONSUME_PARTIAL
        }

        var recipeHolder = recipes.first()
        var resultStack = recipeHolder.value.getResultItem(reg)
        if (recipes.size > 1) {
            for ((idx, rHolder) in recipes.withIndex()) {
                if (idx == 0) continue
                val newResult = rHolder.value.getResultItem(reg)
                if (stack.count >= newResult.count && newResult.count > resultStack.count) {
                    resultStack = newResult.copy()
                    recipeHolder = rHolder
                }
            }
        }

        player.info("Recipe: ${recipeHolder.id}")

        if (stack.count < resultStack.count) {
            player.info("Failed to find recipes with matching output count")
            return ItemInteractionResult.CONSUME_PARTIAL
        }
        for (ing in recipeHolder.value.ingredients) {
            if (ing.isEmpty) continue
            val stack = (ing.items.firstOrNull() ?: ItemStack.EMPTY).copy()
            player.givePlayer(stack)
        }
        stack.shrink(resultStack.count)



        return ItemInteractionResult.SUCCESS
    }

    /*private fun printRecipe(player: Player, rHolder: RecipeHolder<CraftingRecipe>, reg: RegistryAccess) {
        player.msg("Recipe (${rHolder.id}) :")
        for ((idx, ing) in rHolder.value.ingredients.withIndex()) {
            if (!ing.isEmpty) player.msg(buildString {
                append("$idx: ")
                append(ing.items.firstOrNull())
                append(" | ")
                append(
                    Ingredient.CODEC
                        .encodeStart(reg.createSerializationContext(JsonOps.INSTANCE), ing)
                        .result().getOrNull()
                )
            })
        }
    }*/



}

