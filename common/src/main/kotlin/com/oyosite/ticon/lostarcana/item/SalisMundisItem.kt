package com.oyosite.ticon.lostarcana.item

import com.oyosite.ticon.lostarcana.recipe.SalisMundisRecipeInput
import com.oyosite.ticon.lostarcana.recipe.SalisMundisTransformRecipe
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.references.Blocks
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.GameType

class SalisMundisItem(properties: Properties) : Item(properties) {

    override fun useOn(useOnContext: UseOnContext): InteractionResult {
        (useOnContext.level as? ServerLevel)?.let{ level ->
            val pos = useOnContext.clickedPos
            val input = SalisMundisRecipeInput(level, pos)
            val player = useOnContext.player
            if(player?.blockActionRestricted(level, pos, GameType.SURVIVAL)?:false)return@let
            val opt = level.recipeManager.getRecipeFor(SalisMundisTransformRecipe.Type, input, level)
            if(opt.isPresent) opt.get().let { recipeHolder ->
                val recipe = recipeHolder.value
                val resultItem = recipe.assemble(input, level.registryAccess())
                level.setBlock(pos, (Items.AIR as BlockItem).block.defaultBlockState(), 3)
                if (resultItem.item is BlockItem){
                    (resultItem.item as BlockItem).place(BlockPlaceContext(useOnContext))
                } else {
                    val (x,y,z) = pos.center
                    level.addFreshEntity(ItemEntity(level, x,y,z, resultItem))
                }
                return InteractionResult.CONSUME
            }
        }

        return super.useOn(useOnContext)
    }

}