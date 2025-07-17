package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.aspect.Aspect;
import com.oyosite.ticon.lostarcana.aspect.IAspectHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(Item.class)
public class ItemMixin implements IAspectHolder<ItemStack> {

    @Unique
    Aspect[] staticAspects = null;

    @Unique
    Function<ItemStack, Aspect[]> aspectGetter = null;

    @Override
    public Aspect[] lostarcana$getAspects(ItemStack context) {
        Aspect[] o = null;
        if(aspectGetter!=null) o = aspectGetter.apply(context);
        if(o == null) o = staticAspects;
        return o;
    }

    @Override
    public void lostarcana$setStaticAspects(Aspect[] aspects) {
        staticAspects = aspects;
    }

    @Override
    public void lostarcana$setAspectGetter(Function<ItemStack, Aspect[]> getter) {
        aspectGetter = getter;
    }
}
