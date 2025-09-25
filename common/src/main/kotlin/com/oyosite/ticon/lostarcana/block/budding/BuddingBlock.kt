package com.oyosite.ticon.lostarcana.block.budding

import com.oyosite.ticon.lostarcana.unaryPlus
import dev.architectury.registry.registries.RegistrySupplier
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.AmethystClusterBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BuddingAmethystBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Fluids

open class BuddingBlock(
    properties: Properties,
    val smallBud: RegistrySupplier<out Block>,
    val mediumBud: RegistrySupplier<out Block>,
    val largeBud: RegistrySupplier<out Block>,
    val cluster: RegistrySupplier<out Block>,
) : BuddingAmethystBlock(properties) {
    val DIRECTIONS: Array<Direction> = Direction.entries.toTypedArray()


    override fun randomTick(
        blockState: BlockState,
        serverLevel: ServerLevel,
        blockPos: BlockPos,
        randomSource: RandomSource
    ) {
        if (randomSource.nextInt(5) == 0) {
            val direction = DIRECTIONS[randomSource.nextInt(DIRECTIONS.size)]
            val blockPos2 = blockPos.relative(direction)
            val blockState2 = serverLevel.getBlockState(blockPos2)
            var block: Block? = null
            if (canClusterGrowAtState(blockState2)) {
                block = +smallBud
            } else if (blockState2.`is`(+smallBud) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = +mediumBud
            } else if (blockState2.`is`(+mediumBud) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = +largeBud
            } else if (blockState2.`is`(+largeBud) && blockState2.getValue(AmethystClusterBlock.FACING) == direction) {
                block = +cluster
            }

            if (block != null) {
                val blockState3 = block.defaultBlockState()
                    .setValue(AmethystClusterBlock.FACING, direction)
                    .setValue(AmethystClusterBlock.WATERLOGGED, blockState2.fluidState.type === Fluids.WATER)
                serverLevel.setBlockAndUpdate(blockPos2, blockState3)
            }
        }
    }

}