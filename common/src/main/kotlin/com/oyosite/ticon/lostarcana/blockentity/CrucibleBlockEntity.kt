package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.aspect.ASPECT_REGISTRY
import com.oyosite.ticon.lostarcana.aspect.Aspect
import com.oyosite.ticon.lostarcana.aspect.AspectStack
import com.oyosite.ticon.lostarcana.aspect.aspects
import com.oyosite.ticon.lostarcana.recipe.CrucibleRecipe
import com.oyosite.ticon.lostarcana.tag.CRUCIBLE_HEAT_SOURCES
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.RecipeInput
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class CrucibleBlockEntity(blockPos: BlockPos, blockState: BlockState) : BlockEntity(CRUCIBLE_BLOCK_ENTITY.value(), blockPos, blockState), RecipeInput {
    var fluidAmount: Long = 900

    val aspects = mutableMapOf<Aspect, Int>()
    var waterColor = 0

    var usedItem = ItemStack.EMPTY

    var heatLevel = 0
        set(value) {
            field = value
            setChanged()
        }

    fun useItemOn(
        itemStack: ItemStack,
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        player: Player,
        interactionHand: InteractionHand,
        blockHitResult: BlockHitResult
    ): ItemInteractionResult {
        if(heatLevel < 300) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
        usedItem = player.getItemInHand(interactionHand)
        val recipe = level.recipeManager.getRecipeFor(CrucibleRecipe.Type, this, level)
        if(recipe.isPresent){
            val r = recipe.get()
            val r1 = r.value
            val result = r1.assemble(this, level.registryAccess())
            level.addFreshEntity(
                ItemEntity(level, player.x, player.y, player.z, result)
            )
            usedItem.shrink(1)
            r1.aspects.forEach(this::removeAspect)
            waterColor = 0
            return ItemInteractionResult.SUCCESS
        }else if(canDissolve(usedItem)){
            usedItem.aspects.forEach(this::addAspect)
            usedItem.shrink(1)
            waterColor = 0
            return ItemInteractionResult.SUCCESS
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

    fun canDissolve(stack: ItemStack) = stack.aspects.isNotEmpty()

    fun addAspect(aspect: AspectStack) {
        aspects[aspect.aspect] = aspects.getOrElse(aspect.aspect) { 0 } + aspect.amount
        setChanged()
    }
    fun removeAspect(aspect: AspectStack) {
        aspects[aspect.aspect] = aspects.getOrElse(aspect.aspect) { 0 } - aspect.amount
        if(aspects[aspect.aspect]==0)aspects.remove(aspect.aspect)
        setChanged()
    }

    override fun loadAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        fluidAmount = compoundTag.getLong("water")
        val aspectsTag = compoundTag.getCompound("aspects")
        aspects.clear()
        aspectsTag.allKeys.forEach {
            val aspect = ASPECT_REGISTRY.get(Identifier.parse(it)) ?: return@forEach println("Aspect $it was not registered, discarding")
            aspects[aspect] = aspectsTag.getInt(it)
        }
        heatLevel = compoundTag.getInt("heat")
        waterColor = 0
    }

    override fun saveAdditional(compoundTag: CompoundTag, provider: HolderLookup.Provider) {
        compoundTag.putLong("water", fluidAmount)
        val aspectsTag = CompoundTag()
        aspects.forEach { (aspect, amt) -> aspectsTag.putInt(ASPECT_REGISTRY.getKey(aspect)?.toString() ?: return@forEach println("Aspect $aspect was not registered, discarding") , amt) }
        compoundTag.put("aspects", aspectsTag)
        compoundTag.putInt("heat", heatLevel)
        waterColor = 0
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener?>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(provider: HolderLookup.Provider): CompoundTag? {
        return this.saveWithoutMetadata(provider)
    }

    override fun getItem(i: Int): ItemStack = usedItem

    override fun size(): Int = 1

    companion object{
        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState, blockEntity: CrucibleBlockEntity?){
            blockEntity?:return
            if(level.isStateAtPosition(blockPos.below()) {it.`is`(CRUCIBLE_HEAT_SOURCES)}) {
                if (blockEntity.heatLevel < 600) blockEntity.heatLevel += 1
            } else if (blockEntity.heatLevel > 0) blockEntity.heatLevel -= 1
        }
    }
}