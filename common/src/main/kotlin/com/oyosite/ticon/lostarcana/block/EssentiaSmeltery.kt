package com.oyosite.ticon.lostarcana.block

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.AbstractFurnaceBlock
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class EssentiaSmeltery(properties: Properties) : AbstractFurnaceBlock(properties), EntityBlock {
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity? = null

    override fun codec(): MapCodec<EssentiaSmeltery> = CODEC

    override fun openContainer(
        level: Level,
        blockPos: BlockPos,
        player: Player
    ) {

    }

    companion object{
        val CODEC = simpleCodec(::EssentiaSmeltery)
    }
}