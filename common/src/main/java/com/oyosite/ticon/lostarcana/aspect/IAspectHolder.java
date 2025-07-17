package com.oyosite.ticon.lostarcana.aspect;

import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Internal
public interface IAspectHolder<T> {
    AspectStack[] lostarcana$getAspects(T context);
    void lostarcana$setStaticAspects(AspectStack[] aspects);
    void lostarcana$setAspectGetter(Function<T, AspectStack[]> getter);
}
