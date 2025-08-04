package com.oyosite.ticon.lostarcana.emi

import com.mojang.authlib.minecraft.client.MinecraftClient
import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.ARCANE_WORKBENCH
import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import com.oyosite.ticon.lostarcana.unaryPlus
import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.render.EmiTexture
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import net.minecraft.client.Minecraft
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeManager

class ArcaneWorkbenchEmi(@get:JvmName("getId1") val id: Identifier, val ingredients: List<EmiIngredient>, val output: EmiStack) : EmiRecipe {
    constructor(id: Identifier, recipe: ArcaneWorkbenchRecipe): this(
        id,
        recipe.getAllIngredients().map { EmiIngredient.of(it) },
        EmiStack.of(recipe.getResultItem(Minecraft.getInstance().level!!.registryAccess()))
    )

    override fun getCategory(): EmiRecipeCategory = CATEGORY

    override fun getId(): ResourceLocation = id

    override fun getInputs(): List<EmiIngredient> = ingredients

    override fun getOutputs(): List<EmiStack> = listOf(output)

    override fun getDisplayWidth(): Int = 159

    override fun getDisplayHeight(): Int = 54

    override fun addWidgets(widgets: WidgetHolder) {
        listOf(10 to 17, 86 to 17, 86 to 35, 86 to 53, 10 to 53, 10 to 35).forEachIndexed{ i, coords ->
            widgets.addSlot(inputs[i+9], coords.first-9, coords.second-16)
        }
        for(row in 0..2) for(col in 0..2)
            widgets.addSlot(inputs[col+row*3], 21 + col * 18, 1 + row * 18)

        widgets.addSlot(output, 124+23-9, 35-16).recipeContext(this);
    }


    companion object{
        val SPRITE_SHEET = LostArcana.id("textures/gui/arcane_workbench.png")
        val WORKSTATION = EmiStack.of(+ARCANE_WORKBENCH)
        val CATEGORY = EmiRecipeCategory(LostArcana.id("arcane_workbench"), WORKSTATION, EmiTexture(SPRITE_SHEET, 9, 16, 159, 54))
    }
}