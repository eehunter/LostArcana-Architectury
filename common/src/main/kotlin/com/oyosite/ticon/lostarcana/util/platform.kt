package com.oyosite.ticon.lostarcana.util

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.core.Holder
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import java.lang.AssertionError


@ExpectPlatform
fun <T: BlockEntity> platformRegisterBlockEntity(name: String, builder: ()->BlockEntityType.Builder<T>): Holder<BlockEntityType<T>> = throw AssertionError("No platform implementation.")