package com.oyosite.ticon.lostarcana.emi

import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry

@EmiEntrypoint
class LostArcanaEmiPlugin: EmiPlugin {
    override fun register(emiRegistry: EmiRegistry) {
        emiRegistry.addCategory(ArcaneWorkbenchEmi.CATEGORY)
        emiRegistry.addWorkstation(ArcaneWorkbenchEmi.CATEGORY, ArcaneWorkbenchEmi.WORKSTATION)

        val recipeManager = emiRegistry.recipeManager
        for (recipe in recipeManager.getAllRecipesFor(ArcaneWorkbenchRecipe.Type)){
            println(recipe)
            emiRegistry.addRecipe(ArcaneWorkbenchEmi(recipe.id, recipe.value))
        }
    }
}