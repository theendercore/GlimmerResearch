package com.theendercore.glimmer_research.util

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level

fun getRecipeInputs(level: Level, stack: ItemStack): Pair<Int, List<ItemStack>> {
    val reg = level.registryAccess()
    val recipes = level.recipeManager.getAllRecipesFor(RecipeType.CRAFTING)
        .filter { it.value().getResultItem(reg).item === stack.item }
        .reversed()

    if (recipes.isEmpty()) {
        println("Failed to find Any recipes")
        return 0 to listOf()
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

    println("Recipe: ${recipeHolder.id}")

    if (stack.count < resultStack.count) {
        println("Failed to find recipes with matching output count")
        return 0 to listOf()
    }

    val list = mutableListOf<ItemStack>()
    for (ingredient in recipeHolder.value.ingredients) {
        if (ingredient.isEmpty) continue
        val stack = (ingredient.items.firstOrNull() ?: ItemStack.EMPTY).copy()
        if (!stack.isEmpty) {
            list.add(stack)
        }
    }
    return resultStack.count to list
}