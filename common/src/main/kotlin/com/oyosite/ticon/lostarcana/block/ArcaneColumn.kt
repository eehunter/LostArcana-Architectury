package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import com.oyosite.ticon.lostarcana.unaryPlus
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
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet
import net.minecraft.world.level.storage.loot.parameters.LootContextParams

class ArcaneColumn(properties: Properties) : Block(properties), EntityBlock, MultiblockController {

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity =
        ArcaneColumnBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
    override fun deconstruct(levelAccessor: LevelAccessor, blockPos: BlockPos, brokenFrom: BlockPos?) {
        for(i in (-2..2).map { blockPos.above(it) }){
            val block = levelAccessor.getBlockState(i).block
            if(block is ArcaneColumn || block is MultiblockPlaceholder)levelAccessor.setBlock(i, Blocks.AIR.defaultBlockState(), 3)
        }
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

    override fun generateDrops(level: ServerLevel, pos: BlockPos, corePos: BlockPos, player: Player, tool: ItemStack){
        val (x,y,z) = pos.center
        level.server.reloadableRegistries().getLootTable(multiblockLootTable).getRandomItems(
            LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, corePos.center).withParameter(LootContextParams.TOOL, tool).withOptionalParameter(LootContextParams.THIS_ENTITY, player).withOptionalParameter(
                LootContextParams.BLOCK_ENTITY, level.getBlockEntity(corePos)).create(LootContextParamSet.builder().build()))?.map { ItemEntity(level, x,y,z, it) }
            ?.forEach(level::addFreshEntity)
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

    override fun construct(context: UseOnContext): Boolean {
        val level = context.level
        var bottomPos = context.clickedPos.below(5)
        var state: BlockState
        var yOffset = 0
        do{
            bottomPos = bottomPos.above()
            state = level.getBlockState(bottomPos)
        }while (state != (+ARCANE_STONE_PILLAR).defaultBlockState() && yOffset++ < 5)
        if(context.clickedPos.y < bottomPos.y) throw IllegalStateException("Arcane Column construct function was called, even though there is no pillar block for it to have been called on.")
        for(i in 0..4){
            if(level.getBlockState(bottomPos.above(i)) != (+ARCANE_STONE_PILLAR).defaultBlockState()) return false
        }
        for(i in 0..4){
            val newBlock = (if(i == 2) this else +MULTIBLOCK_PLACEHOLDER).defaultBlockState()
            level.setBlock(bottomPos.above(i), newBlock, 3)
            if(i!=2){
                level.setBlockEntity(PlaceholderBlockEntity(bottomPos.above(i), (+MULTIBLOCK_PLACEHOLDER).defaultBlockState()).apply { linkedPos = bottomPos.above(2); linkedBlock = this@ArcaneColumn })
            }
        }
        return true
    }




    companion object{
        val multiblockLootTable: ResourceKey<LootTable> = ResourceKey.create(Registries.LOOT_TABLE, LostArcana.id("blocks/multiblock/arcane_column"))
    }

}