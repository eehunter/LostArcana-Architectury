package com.oyosite.ticon.lostarcana.emi

import com.oyosite.ticon.lostarcana.item.SALIS_MUNDIS
import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import com.oyosite.ticon.lostarcana.recipe.SalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.client.Minecraft
import net.minecraft.world.item.crafting.Ingredient

@EmiEntrypoint
class LostArcanaEmiPlugin: EmiPlugin {
    override fun register(emiRegistry: EmiRegistry) {
        emiRegistry.addCategory(ArcaneWorkbenchEmi.CATEGORY)
        emiRegistry.addWorkstation(ArcaneWorkbenchEmi.CATEGORY, ArcaneWorkbenchEmi.WORKSTATION)

        val recipeManager = emiRegistry.recipeManager
        for (recipe in recipeManager.getAllRecipesFor(ArcaneWorkbenchRecipe.Type)){
            emiRegistry.addRecipe(ArcaneWorkbenchEmi(recipe.id, recipe.value))
        }

        for (recipe in recipeManager.getAllRecipesFor(SalisMundisTransformRecipe.Type)){
            emiRegistry.addRecipe(EmiWorldInteractionRecipe.builder().id(recipe.id).leftInput(EmiIngredient.of(
                Ingredient.of(+SALIS_MUNDIS))).rightInput(EmiIngredient.of(recipe.value.ingredients.first()), false).output(EmiStack.of(recipe.value.getResultItem(
                Minecraft.getInstance().level!!.registryAccess()))).build())
        }
    }
}