package com.oyosite.ticon.lostarcana.aspect;

import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Function;

@ApiStatus.Internal
public interface IAspectHolder<T> {
    List<AspectStack> lostarcana$getAspects(T context);
    void lostarcana$setStaticAspects(List<AspectStack> aspects);
    void lostarcana$setAspectGetter(Function<T, List<AspectStack>> getter);
}
