package com.oyosite.ticon.lostarcana.block

import com.oyosite.ticon.lostarcana.BlockProperties
import com.oyosite.ticon.lostarcana.block.budding.BuddingBlock
import com.oyosite.ticon.lostarcana.block.budding.ClusterBlock
import com.oyosite.ticon.lostarcana.unaryPlus
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction

class CustomBuddingBlockCollection(baseName: String, mapColor: MapColor) {
    val block = "${baseName}_block" % { Block(BlockProperties.ofFullCopy(Blocks.AMETHYST_BLOCK)) } % {}
    val cluster = "${baseName}_cluster" % { ClusterBlock(7.0F, 3.0F, prop
				.mapColor(mapColor)
				.forceSolidOn()
				.noOcclusion()
				.sound(SoundType.AMETHYST_CLUSTER)
				.strength(1.5F)
				.lightLevel { 5 }
				.pushReaction(PushReaction.DESTROY)) }
    val largeBud = "large_${baseName}_bud" % { ClusterBlock(5.0F, 3.0F, BlockProperties.ofLegacyCopy(+cluster).sound(SoundType.MEDIUM_AMETHYST_BUD).lightLevel { 4 }) }
    val mediumBud = "medium_${baseName}_bud" % { ClusterBlock(4.0F, 3.0F, BlockProperties.ofLegacyCopy(+cluster).sound(SoundType.LARGE_AMETHYST_BUD).lightLevel { 2 }) }
    val smallBud = "small_${baseName}_bud" % { ClusterBlock(3.0F, 4.0F, BlockProperties.ofLegacyCopy(+cluster).sound(SoundType.SMALL_AMETHYST_BUD).lightLevel { 1 }) }
    val buddingBlock = "budding_${baseName}" % { BuddingBlock(
        BlockProperties.ofFullCopy(Blocks.BUDDING_AMETHYST),
        smallBud, mediumBud, largeBud, cluster
    ) } % {}


}