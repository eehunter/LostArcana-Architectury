package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.aspect.AspectStack;
import com.oyosite.ticon.lostarcana.aspect.IAspectHolder;
import com.oyosite.ticon.lostarcana.aspect.ItemAspectHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.function.Function;

@Mixin(Item.class)
public class ItemMixin implements IAspectHolder<ItemStack> {

    @Unique
    private List<AspectStack> lostarcana$staticAspects = null;

    @Unique
    private Function<ItemStack, List<AspectStack>> lostarcana$aspectGetter = null;

    @Override
    public List<AspectStack> lostarcana$getAspects(ItemStack context) {
        List<AspectStack> o = null;
        if(this instanceof ItemAspectHolder) o = ((ItemAspectHolder) this).getAspects(context);
        if(o == null && lostarcana$aspectGetter !=null) o = lostarcana$aspectGetter.apply(context);
        if(o == null) o = lostarcana$staticAspects;
        return o;
    }

    @Override
    public void lostarcana$setStaticAspects(List<AspectStack> aspects) {
        lostarcana$staticAspects = aspects;
    }

    @Override
    public void lostarcana$setAspectGetter(Function<ItemStack, List<AspectStack>> getter) {
        lostarcana$aspectGetter = getter;
    }
}
