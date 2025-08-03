package com.oyosite.ticon.lostarcana.neoforge.mixin;

import com.oyosite.ticon.lostarcana.blockentity.ArcaneColumnBlockEntity;
import com.oyosite.ticon.lostarcana.client.blockentity.ArcaneColumnRenderer;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.extensions.IBlockEntityRendererExtension;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArcaneColumnRenderer.class)
public class ArcaneColumnMixin implements IBlockEntityRendererExtension<ArcaneColumnBlockEntity> {
    @Override
    public @NotNull AABB getRenderBoundingBox(@NotNull ArcaneColumnBlockEntity blockEntity) {
        return AABB.encapsulatingFullBlocks(blockEntity.getBlockPos().below(2), blockEntity.getBlockPos().above(2));
    }
}
