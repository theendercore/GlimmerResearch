package com.theendercore.glimmer_research.util

import com.theendercore.glimmer_research.GlimmerResearch
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.level.Level
import kotlin.math.min

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

    val ingredients = extractIngredients(recipeHolder.value) ?: return 0 to listOf()

    println(ingredients)

    return resultStack.count to ingredients
}

fun extractIngredients(recipe: CraftingRecipe): MutableList<ItemStack>? {
    val list = ArrayList<ItemStack>(9)
    loop@ for (ingredient in recipe.ingredients) {
        if (ingredient.isEmpty) continue

        val newStack = getItemFromIngredient(ingredient)
        if (newStack.isEmpty) continue


        if (newStack.isStackable && !list.isEmpty()) {
            for (listStack in list) {
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
        list.add(newStack)
    }
    return list
}

private fun getItemFromIngredient(ingredient: Ingredient): ItemStack {
    val ci = ingredient.customIngredient
    if (ci != null) {
        GlimmerResearch.log.info("Custom ingredient detected! [${ci.serializer.identifier}]")
    }
    for (stack in ingredient.items) {
        // TODO make this switch recipes
//        if (stack.item.hasCraftingRemainingItem()) continue
        return stack.copy()
    }

    return ItemStack.EMPTY
}