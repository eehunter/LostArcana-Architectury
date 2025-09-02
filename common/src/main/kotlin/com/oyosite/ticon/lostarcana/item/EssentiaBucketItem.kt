package com.oyosite.ticon.lostarcana.item

import dev.architectury.core.item.ArchitecturyBucketItem
import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluid
import net.minecraft.world.phys.BlockHitResult
import java.util.function.Supplier

class EssentiaBucketItem(fluid: ()-> Fluid, properties: Properties) : ArchitecturyBucketItem(fluid, properties) {

    /*override fun finishUsingItem(itemStack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack? {
        return super.finishUsingItem(itemStack, level, livingEntity)
    }

    override fun emptyContents(
        player: Player?,
        level: Level,
        blockPos: BlockPos,
        blockHitResult: BlockHitResult?
    ): Boolean {
        if(!super.emptyContents(player, level, blockPos, blockHitResult))return false
        //level.getBlockEntity(blockPos)?.applyComponentsFromItemStack(blockHitResult?)
        return true
    }*/


}