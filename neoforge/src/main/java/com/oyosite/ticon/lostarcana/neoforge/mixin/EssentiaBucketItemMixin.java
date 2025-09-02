package com.oyosite.ticon.lostarcana.neoforge.mixin;

import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneColumnRenderer;
import com.oyosite.ticon.lostarcana.item.EssentiaBucketItem;
import com.oyosite.ticon.lostarcana.item.neoforge.ItemUtilsKt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.extensions.IDispensibleContainerItemExtension;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EssentiaBucketItem.class)
public abstract class EssentiaBucketItemMixin extends BucketItem {
    public EssentiaBucketItemMixin(Fluid arg, Properties arg2) {
        super(arg, arg2);
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult hitResult, @Nullable ItemStack container) {
        if(!super.emptyContents(player, level, pos, hitResult, container))return false;
        else {
            ItemUtilsKt.emptyContents(player, level, pos, hitResult, container);
            return true;
        }
    }
}
