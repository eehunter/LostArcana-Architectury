package com.oyosite.ticon.lostarcana.aspect;

import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Internal
public interface IAspectHolder<T> {
    Aspect[] lostarcana$getAspects(T context);
    void lostarcana$setStaticAspects(Aspect[] aspects);
    void lostarcana$setAspectGetter(Function<T, Aspect[]> getter);
}
