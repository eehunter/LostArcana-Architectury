package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity
import com.oyosite.ticon.lostarcana.blockentity.PLACEHOLDER_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.PlaceholderBlockEntity
import com.oyosite.ticon.lostarcana.unaryPlus
import com.oyosite.ticon.lostarcana.util.component1
import com.oyosite.ticon.lostarcana.util.component2
import com.oyosite.ticon.lostarcana.util.component3
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.item.ItemEntity
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
import kotlin.jvm.optionals.getOrNull

class ArcaneColumn(properties: Properties) : Block(properties), EntityBlock, MultiblockController {

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity =
        ArcaneColumnBlockEntity(blockPos, blockState)

    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
    override fun deconstruct(levelAccessor: LevelAccessor, blockPos: BlockPos, brokenFrom: BlockPos?, bl: Boolean) {
        println("Deconstructing column")
        for(i in (-2..2).map { blockPos.above(it) }){
            val block = levelAccessor.getBlockState(i).block
            if(block is ArcaneColumn || block is MultiblockPlaceholder)levelAccessor.setBlock(i, Blocks.AIR.defaultBlockState(), 3)
        }
        val level = levelAccessor as? ServerLevel ?: return
        val (x,y,z) = (brokenFrom?:blockPos).center


        if(bl)
            level.server.reloadableRegistries().getLootTable(multiblockLootTable).getRandomItems(
                LootParams.Builder(level).create(LootContextParamSet.builder().build()))?.map { ItemEntity(level, x,y,z, it) }
                ?.forEach(level::addFreshEntity)
            //
            // LootContext.Builder().create( Optional.of(multiblockLootTable.location())))
    }

    override fun onRemove(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        blockState2: BlockState,
        bl: Boolean
    ) {
        deconstruct(level, blockPos, blockPos, bl)
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
                level.setBlockEntity(PlaceholderBlockEntity(bottomPos.above(i), (+MULTIBLOCK_PLACEHOLDER).defaultBlockState()).apply { linkedBlock = bottomPos.above(2) })
            }
        }
        return true
    }




    companion object{
        val multiblockLootTable: ResourceKey<LootTable> = ResourceKey.create(Registries.LOOT_TABLE, LostArcana.id("blocks/multiblock/arcane_column"))
    }

}