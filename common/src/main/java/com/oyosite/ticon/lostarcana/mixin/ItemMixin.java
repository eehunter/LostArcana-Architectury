package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.aspect.AspectStack;
import com.oyosite.ticon.lostarcana.aspect.IAspectHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Function;

@Mixin(Item.class)
public class ItemMixin implements IAspectHolder<ItemStack> {

    @Unique
    private AspectStack[] lostarcana$staticAspects = null;

    @Unique
    private Function<ItemStack, AspectStack[]> lostarcana$aspectGetter = null;

    @Override
    public AspectStack[] lostarcana$getAspects(ItemStack context) {
        AspectStack[] o = null;
        if(lostarcana$aspectGetter !=null) o = lostarcana$aspectGetter.apply(context);
        if(o == null) o = lostarcana$staticAspects;
        return o;
    }

    @Override
    public void lostarcana$setStaticAspects(AspectStack[] aspects) {
        lostarcana$staticAspects = aspects;
    }

    @Override
    public void lostarcana$setAspectGetter(Function<ItemStack, AspectStack[]> getter) {
        lostarcana$aspectGetter = getter;
    }
}
