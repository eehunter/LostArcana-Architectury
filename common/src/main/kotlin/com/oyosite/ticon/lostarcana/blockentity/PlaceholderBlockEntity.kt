package com.oyosite.ticon.lostarcana.blockentity

import com.oyosite.ticon.lostarcana.Identifier
import com.oyosite.ticon.lostarcana.block.MULTIBLOCK_PLACEHOLDER
import com.oyosite.ticon.lostarcana.block.MultiblockController
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PlaceholderBlockEntity(pos: BlockPos, state: BlockState = (+MULTIBLOCK_PLACEHOLDER).defaultBlockState()): BlockEntity(PLACEHOLDER_BLOCK_ENTITY.value(), pos, state) {

    var linkedPos: BlockPos = pos
        set(value) {
            if (field!=value) setChanged()
            field = value
        }

    var linkedBlock: MultiblockController? = null
        set(value) {
            if (field!=value) setChanged()
            field = value
        }


    override fun loadAdditional(tag: CompoundTag, provider: HolderLookup.Provider) {
        linkedPos = BlockPos.of(tag.getLong("linkPos"))
        val linkBlockId = Identifier.parse(tag.getString("linkBlock"))
        val linkBlock = linkBlockId.takeIf(BuiltInRegistries.BLOCK::containsKey)?.let(BuiltInRegistries.BLOCK::get)
        linkedBlock = linkBlock as? MultiblockController
    }

    override fun saveAdditional(tag: CompoundTag, provider: HolderLookup.Provider) {
        tag.putLong("linkPos", linkedPos.asLong())
        val lb = linkedBlock
        if(lb is Block){
            val linkBlockId = BuiltInRegistries.BLOCK.getKey(lb)
            tag.putString("linkBlock", linkBlockId.toString())
        }
    }

}