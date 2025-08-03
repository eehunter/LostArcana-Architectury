package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.block.entity.FilteredSlot
import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.recipe.ArcaneWorkbenchRecipe
import com.oyosite.ticon.lostarcana.util.Slot
import com.oyosite.ticon.lostarcana.util.testCrystalInSlot
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.Container
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.player.StackedContents
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.Level

class ArcaneWorkbenchMenu(val id: Int, val inventory: Inventory, val container: ArcaneWorkbenchRecipeContainer.Wrapper, val ctx: ContainerLevelAccess = ContainerLevelAccess.NULL): RecipeBookMenu<RecipeInput, Recipe<in RecipeInput>>(ARCANE_WORKBENCH_MENU_SCREEN.value(), id) {
    constructor(id: Int, inventory: Inventory): this(id, inventory, ArcaneWorkbenchRecipeContainer().wrap(inventory.player))

    private val player = inventory.player

    init {
        container.markDirtyCallback = {slotsChanged(container)}
        container.startOpen(player)
        addSlot(ArcaneWorkbenchResultSlot(player, container, container.result, 0, 124+23, 35))

        listOf(10 to 17, 86 to 17, 86 to 35, 86 to 53, 10 to 53, 10 to 35).forEachIndexed{ i, coords ->
            addSlot(FilteredSlot(container, 9+i, coords.first, coords.second, i::testCrystalInSlot){slotsChanged(container)})
        }

        addSlot(FilteredSlot(container, 15, 119, 53, { it.item is CastingItem }))

        //Crafting slots
        for(row in 0..2) for(col in 0..2)
            addSlot(Slot(container, col+row*3, 30 + col * 18, 17 + row * 18){slotsChanged(container)})
        //Inventory slots
        for(row in 0..2) for(col in 0..8)
            addSlot(Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
        //Hotbar slots
        for(slot in 0..8)
            addSlot(Slot(inventory, slot, 8 + slot * 18, 142))

        slotsChanged(container)

    }

    override fun slotsChanged(container: Container) {
        updateResult(this, player.level(), player, this.container)
        super.slotsChanged(container)
    }


    override fun quickMoveStack(
        player: Player,
        i: Int
    ): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots.getOrNull(i)
        if(slot?.hasItem()?:false){

            val original = slot.item
            newStack = original.copy()
            if(i==0) {
                ctx.execute { level, pos -> original.item.onCraftedBy(original, level, player) }
                if (!this.moveItemStackTo(original, this.container.containerSize, this.slots.size, true)) return ItemStack.EMPTY
                slot.onQuickCraft(original, newStack)
            } else if(i < container.containerSize) {
                if (!this.moveItemStackTo(original, this.container.containerSize, this.slots.size, true)) return ItemStack.EMPTY;
            } else if(!this.moveItemStackTo(original, 0, this.container.containerSize, false)) {
                return ItemStack.EMPTY;
            }
            if(original.isEmpty)slot.set(ItemStack.EMPTY)
            else slot.setChanged()
            slot.onTake(player, original)
            if (i == 0) {
                player.drop(original, false)
            }
        }
        return newStack
    }

    override fun stillValid(player: Player): Boolean = container.stillValid(player)
    override fun fillCraftSlotsStackedContents(stackedContents: StackedContents) {
        container.baseCraftingContainer.fillStackedContents(stackedContents)
        container.setChanged()
    }

    override fun clearCraftingContent() {
        container.clearContent()
        container.result.clearContent()
    }

    override fun recipeMatches(recipeHolder: RecipeHolder<Recipe<in RecipeInput>>): Boolean {
        val recipe = recipeHolder.value
        return when(recipeHolder.value.type){
            ArcaneWorkbenchRecipe.Type -> recipe.matches(container, player.level())
            RecipeType.CRAFTING -> recipe.matches(container.baseCraftingContainer.asCraftInput(), player.level())
            else -> false
        }
    }

    override fun getResultSlotIndex(): Int = 0

    override fun getGridWidth(): Int = 3

    override fun getGridHeight(): Int = 3

    override fun getSize(): Int = 10

    override fun getRecipeBookType(): RecipeBookType = RecipeBookType.CRAFTING

    override fun shouldMoveToInventory(i: Int): Boolean = i != resultSlotIndex

    companion object{
        protected fun updateResult(menu: ArcaneWorkbenchMenu, level: Level, player: Player, container: ArcaneWorkbenchRecipeContainer.Wrapper){
            if(level.isClientSide)return
            val craftingInput = container.baseCraftingContainer.asCraftInput()
            var recipe: RecipeHolder<ArcaneWorkbenchRecipe>? = null
            assert(player is ServerPlayer) { "Player is not ServerPlayer on server. (This should not happen)" }
            var itemStack = ItemStack.EMPTY
            val optional = level.server!!.recipeManager.getRecipeFor(ArcaneWorkbenchRecipe.Type, container, level)
            optional.ifPresent{
                println("updating result")
                recipe = it
                if(container.result.setRecipeUsed(level, player as ServerPlayer, it)){
                    itemStack = it.value.assemble(container, level.registryAccess()).takeIf { it.isItemEnabled(level.enabledFeatures()) } ?: ItemStack.EMPTY
                }
            }
            if(recipe==null){
                val optional = level.server!!.recipeManager.getRecipeFor(RecipeType.CRAFTING, craftingInput, level)
                optional.ifPresent {
                    if(container.result.setRecipeUsed(level, player as ServerPlayer, it)){
                        itemStack = it.value.assemble(craftingInput, level.registryAccess()).takeIf { it.isItemEnabled(level.enabledFeatures()) } ?: ItemStack.EMPTY
                    }
                }
            }

            container.result.setItem(0, itemStack)
            menu.setRemoteSlot(0, itemStack)
            (player as ServerPlayer).connection.send(
                ClientboundContainerSetSlotPacket(menu.id, menu.incrementStateId(), 0, itemStack)
            )

        }
    }
}