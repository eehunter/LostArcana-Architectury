package com.oyosite.ticon.lostarcana.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.oyosite.ticon.lostarcana.aspect.PRIMAL_ASPECTS
import com.oyosite.ticon.lostarcana.blockentity.ArcaneWorkbenchRecipeContainer
import com.oyosite.ticon.lostarcana.item.ASPECT_COMPONENT
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.VIS_CRYSTAL
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingRecipe
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.crafting.RecipeType
import net.minecraft.world.item.crafting.ShapedRecipe
import net.minecraft.world.level.Level

@JvmRecord
data class ArcaneWorkbenchRecipe(val base: CraftingRecipe, val visCost: List<Int>, val auraCost: Float): Recipe<ArcaneWorkbenchRecipeContainer.Wrapper> {
    constructor(base: Recipe<*>, visCost: List<Int>, auraCost: Float): this(base  as? CraftingRecipe ?: throw IllegalArgumentException("Base recipe must be a crafting recipe."), visCost, auraCost)

    override fun matches(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        level: Level
    ): Boolean {
        val focus = recipeInput.castingItem
        val castingItem = focus.item

        //println(base.matches(recipeInput.baseCraftingContainer.asCraftInput(), level))

        if(auraCost != 0f && (focus.isEmpty || castingItem !is CastingItem))return false
        if(recipeInput.getAura(level) < auraCost) return false
        for(i in 0 until 6) if(visCost[i]>0 && visCost[i] > recipeInput[i+9].count)return false

        return base.matches(recipeInput.baseCraftingContainer.asCraftInput(), level)
    }

    override fun assemble(
        recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper,
        provider: HolderLookup.Provider
    ): ItemStack = base.assemble(recipeInput.baseCraftingContainer.asCraftInput(), provider)

    override fun canCraftInDimensions(i: Int, j: Int): Boolean = base.canCraftInDimensions(i, j)

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = base.getResultItem(provider)

    override fun getRemainingItems(recipeInput: ArcaneWorkbenchRecipeContainer.Wrapper): NonNullList<ItemStack?>? {
        return base.getRemainingItems(recipeInput.baseCraftingContainer.asCraftInput())
    }

    fun getAllIngredients(): NonNullList<Ingredient> {
        val baseIngredients = base.ingredients
        val positionedBasedIngredients = mutableListOf<Ingredient>()
        if(base is ShapedRecipe){
            for(j in 0 until 3)for(i in 0 until 3){
                positionedBasedIngredients.add(if(i>=base.width) Ingredient.EMPTY else baseIngredients.getOrElse(i+j*base.width){ Ingredient.EMPTY })
            }
        } else positionedBasedIngredients.addAll(baseIngredients)
        //if(base.canCraftInDimensions(2,3)){
        //    if(base.canCraftInDimensions(1,3))baseIngredients.add(1, Ingredient.EMPTY)
            //baseIngredients.add(1, Ingredient.EMPTY)
            //if(!base.canCraftInDimensions(2,2))baseIngredients.add()
        //}
        val crystals = visCost.mapIndexed { i, count -> ItemStack(VIS_CRYSTAL.get(), count).apply { set(ASPECT_COMPONENT, +PRIMAL_ASPECTS[i]) } }

        val i1 = MutableList(15){ if(it in 0 until 9)positionedBasedIngredients.getOrElse(it){ Ingredient.EMPTY } else Ingredient.of(crystals[it-9]) }
        val i2 = NonNullList.withSize(15, Ingredient.EMPTY)
        i1.forEachIndexed(i2::set)
        return i2
    }

    override fun getSerializer(): RecipeSerializer<ArcaneWorkbenchRecipe> = Serializer as RecipeSerializer<ArcaneWorkbenchRecipe>

    override fun getType(): RecipeType<*> = Type

    object Type: RecipeType<ArcaneWorkbenchRecipe>{
        override fun toString(): String = "lostarcana:arcane_workbench"
    }

    object Serializer: RecipeSerializer<ArcaneWorkbenchRecipe>{


        val CODEC: Codec<ArcaneWorkbenchRecipe> = RecordCodecBuilder.create {
            it.group(
                CraftingRecipe.CODEC.fieldOf("base").forGetter { it.base },
                Codec.list(Codec.INT).fieldOf("crystalCost").forGetter { it.visCost.toList() },
                Codec.FLOAT.fieldOf("visCost").forGetter { it.auraCost }
            ).apply(it, ::ArcaneWorkbenchRecipe)
        }

        val MAP_CODEC = MapCodec.assumeMapUnsafe(CODEC)

        override fun codec(): MapCodec<ArcaneWorkbenchRecipe> = MAP_CODEC

        val STREAM_CODEC = StreamCodec.of<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe>({ buf, recipe ->
            CraftingRecipe.STREAM_CODEC.encode(buf, recipe.base)
            recipe.visCost.forEach(buf::writeInt)
            buf.writeFloat(recipe.auraCost)
        }){ buf ->
            val base = CraftingRecipe.STREAM_CODEC.decode(buf) as CraftingRecipe
            val visCost = List(6){buf.readInt()}
            val auraCost = buf.readFloat()
            ArcaneWorkbenchRecipe(base,visCost,auraCost)
        }

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, ArcaneWorkbenchRecipe> = STREAM_CODEC

    }
}