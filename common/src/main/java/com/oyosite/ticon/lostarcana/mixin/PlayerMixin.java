package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.attribute.AttributesKt;
import dev.architectury.platform.Platform;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @SuppressWarnings("unchecked")
    @Inject(method = "createAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void lostarcana$addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        //if(Platform.isFabric())
        //AttributesKt.getATTRIBUTE_REGISTRY().register();
        //cir.getReturnValue().add(AttributesKt.getARCANE_SIGHT()).add(AttributesKt.getARCANE_INSIGHT());
    }
}
