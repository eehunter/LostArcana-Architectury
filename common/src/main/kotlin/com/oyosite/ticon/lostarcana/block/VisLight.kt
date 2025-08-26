package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.blockentity.VIS_LIGHT_BLOCK_ENTITY
import com.oyosite.ticon.lostarcana.blockentity.VisLightBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponents
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.item.component.DyedItemColor
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

class VisLight(properties: Properties) : Block(properties), EntityBlock {
    override fun getRenderShape(blockState: BlockState): RenderShape = RenderShape.INVISIBLE
    override fun newBlockEntity(
        blockPos: BlockPos,
        blockState: BlockState
    ): BlockEntity = VisLightBlockEntity(blockPos, blockState)

    val defaultColor: DyedItemColor by lazy {this.asItem().components().getOrDefault(DataComponents.DYED_COLOR, DyedItemColor(DEFAULT_COLOR, false) )}

    override fun getShape(
        blockState: BlockState,
        blockGetter: BlockGetter,
        blockPos: BlockPos,
        collisionContext: CollisionContext
    ): VoxelShape = SHAPE

    override fun animateTick(blockState: BlockState, level: Level, blockPos: BlockPos, randomSource: RandomSource) {
        //if(level.gameTime%3L != 0L)return
        val color = (level.getBlockEntity(blockPos) as? VisLightBlockEntity)?.components()?.get(DataComponents.DYED_COLOR)?: (blockState.block as VisLight).defaultColor

        level.addParticle(DustParticleOptions(Vec3.fromRGB24(color.rgb).toVector3f(), 1f), blockPos.x + .5, blockPos.y + .5, blockPos.z + .5, .01, .01, .01)
    }

    override fun randomTick(
        blockState: BlockState,
        level: ServerLevel,
        blockPos: BlockPos,
        randomSource: RandomSource
    ) {


    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        blockState: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return if(type == VIS_LIGHT_BLOCK_ENTITY) BlockEntityTicker(VisLight::tick) as BlockEntityTicker<T> else null
    }

    companion object{
        val DEFAULT_COLOR = -1

        val SHAPE = box(5.0,5.0,5.0,11.0,11.0,11.0)

        fun tick(level: Level, blockPos: BlockPos, blockState: BlockState, entity: VisLightBlockEntity) {
            //super.tick(blockState, level, blockPos, randomSource)
            //val level = world as? ServerLevel ?: return
            //if(!level.isClientSide) return
            //if(level.gameTime%10L != 0L)return
            //println("ticking VisLight")

            //val color = (level.getBlockEntity(blockPos) as? VisLightBlockEntity)?.components()?.get(DataComponents.DYED_COLOR)?: (blockState.block as VisLight).defaultColor
            //level.addParticle(DustParticleOptions(Vec3.fromRGB24(color.rgb).toVector3f(), 1f), blockPos.x + .5, blockPos.y + .5, blockPos.z + .5, .01, .01, .01)


            /*val color = (level.getBlockEntity(blockPos) as? VisLightBlockEntity)?.components()?.get(DataComponents.DYED_COLOR)?: (blockState.block as VisLight).defaultColor
            level.getPlayers{ it.shouldRenderAtSqrDistance(blockPos.center.distanceToSqr(it.position())) }.forEach {
                level.sendParticles(it, DustParticleOptions(Vec3.fromRGB24(color.rgb).toVector3f(), 1f), false, blockPos.x + .5, blockPos.y + .5, blockPos.z + .5, 1, .01, .01, .01, .1)
            }*/
        }
    }
}