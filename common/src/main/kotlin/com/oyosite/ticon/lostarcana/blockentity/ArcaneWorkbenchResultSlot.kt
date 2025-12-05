package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ResultSlot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeType

class ArcaneWorkbenchResultSlot(
    player: Player, val input: ArcaneWorkbenchRecipeContainer.Wrapper, container: Container, i: Int, j: Int, k: Int, val markDirtyCallback: ()->Unit = {}
) : ResultSlot(
    player, input.baseCraftingContainer, container, i, j, k
) {

    override fun onQuickCraft(itemStack: ItemStack, i: Int) {
        super.onQuickCraft(itemStack, i)
    }

    override fun onTake(player: Player, stack: ItemStack) {
        val level = player.level()
        val manager = level.recipeManager
        val opt = manager.getRecipeFor(ArcaneWorkbenchRecipe.Type, input, player.level())
        stack.onCraftedBy(player.level(), player, stack.count)
        val positioned = input.baseCraftingContainer.asPositionedCraftInput()
        val craftInput = positioned.input
        val top = positioned.top
        val left = positioned.left
        val remainder = if(opt.isPresent) manager.getRemainingItemsFor(ArcaneWorkbenchRecipe.Type, input, level) else manager.getRemainingItemsFor(RecipeType.CRAFTING, craftInput, level)

        opt.ifPresent {
            val recipe = it.value
            recipe.visCost.forEachIndexed { i, amt -> input[9+i].shrink(amt) }
            input.container.be?.also{ be ->
                if(be.level==null)return@also
                input.drainAura(be.level!!, recipe.auraCost)
            }
        }
        for(ly in 0 until craftInput.height())for (lx in 0 until craftInput.width()) {
            val i = lx + left + (ly + top) * 3
            var stack1 = input[i]
            val stack2 = remainder[lx + ly * craftInput.width()]
            if(!stack1.isEmpty){
                input.removeItem(i, 1)
                stack1 = input[i]
            }
            if(stack2.isEmpty)continue
            if(stack1.isEmpty){
                input.setItem(i, stack2)
                continue
            }
            if(ItemStack.isSameItemSameComponents(stack1, stack2)){
                stack2.grow(stack1.count)
                input.setItem(i, stack2)
                continue
            }
            if(player.inventory.add(stack2))continue
            player.drop(stack2, false)
        }
        input.setChanged()
    }
}