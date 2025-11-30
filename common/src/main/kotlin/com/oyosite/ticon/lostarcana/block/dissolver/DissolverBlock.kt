package com.oyosite.ticon.lostarcana.block.dissolver

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.block.DISSOLVER_PLACEHOLDER
import com.oyosite.ticon.lostarcana.block.MULTIBLOCK_PLACEHOLDER
import com.oyosite.ticon.lostarcana.block.MultiblockController
import com.oyosite.ticon.lostarcana.block.MultiblockPlaceholder
import com.oyosite.ticon.lostarcana.block.ShapeDelegate
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.stats.Stats
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParams

class DissolverBlock(properties: Properties) : Block(properties), EntityBlock, MultiblockController  {

    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = DissolverBlockEntity(blockPos, blockState)

    override fun deconstruct(
        levelAccessor: LevelAccessor,
        blockPos: BlockPos,
        brokenFrom: BlockPos?
    ) {
        for(i in (0..1).map { blockPos.above(it) }){
            val block = levelAccessor.getBlockState(i).block
            if(block is DissolverBlock || block is MultiblockPlaceholder)levelAccessor.setBlock(i, Blocks.AIR.defaultBlockState(), 3)
        }
    }

    override fun construct(context: UseOnContext): Boolean {
        return true
    }

    override fun onPlace(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        super.onPlace(blockState, level, blockPos, blockState2, bl)
        if (!level.getBlockState(blockPos.above()).canBeReplaced()) return
        level.setBlock(blockPos.above(), DISSOLVER_PLACEHOLDER.get().defaultBlockState(), 3)
        level.setBlockEntity(PlaceholderBlockEntity(blockPos.above(), DISSOLVER_PLACEHOLDER.get().defaultBlockState()).apply { linkedPos = blockPos; linkedBlock = this@DissolverBlock })
    }

    override fun canSurvive(blockState: BlockState, levelReader: LevelReader, blockPos: BlockPos): Boolean {
        //if ((levelReader.getBlockEntity(blockPos.above()) as? PlaceholderBlockEntity)?.linkedPos != blockPos) return false
        return super.canSurvive(blockState, levelReader, blockPos)
    }

    override fun playerDestroy(
        level: Level,
        player: Player,
        blockPos: BlockPos,
        blockState: BlockState,
        blockEntity: BlockEntity?,
        itemStack: ItemStack
    ) {
        player.awardStat(Stats.BLOCK_MINED.get(this))
        player.causeFoodExhaustion(0.005f)
        if(level is ServerLevel) generateDrops(level, blockPos, blockPos, player, itemStack)
    }

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        deconstruct(level, blockPos, blockPos)
        super.onRemove(blockState, level, blockPos, blockState2, bl)
    }

    override fun generateDrops(
        level: ServerLevel,
        pos: BlockPos,
        corePos: BlockPos,
        player: Player,
        tool: ItemStack
    ) {
        val (x,y,z) = pos.center
        level.server.reloadableRegistries().getLootTable(multiblockLootTable).getRandomItems(
            LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, corePos.center).withParameter(LootContextParams.TOOL, tool).withOptionalParameter(LootContextParams.THIS_ENTITY, player).withOptionalParameter(
                LootContextParams.BLOCK_ENTITY, level.getBlockEntity(corePos)).create(MultiblockPlaceholder.lootParamSet))?.map { ItemEntity(level, x,y,z, it) }
            ?.forEach(level::addFreshEntity)
    }

    /*override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape? {
        return shape//super.getShape(blockState, blockGetter, blockPos, collisionContext)
    }*/

    /*override fun shape(
        pos: BlockPos,
        controllerPos: BlockPos
    ): VoxelShape = shape*/

    companion object{
        val multiblockLootTable: ResourceKey<LootTable> = ResourceKey.create(Registries.LOOT_TABLE, LostArcana.id("blocks/multiblock/dissolver"))

    //Shapes.join(box(4.0,16.0,4.0,12.0,24.0,12.0), Shapes.block(), BooleanOp.OR)
    }
}