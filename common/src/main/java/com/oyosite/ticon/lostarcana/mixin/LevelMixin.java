package com.oyosite.ticon.lostarcana.mixin;

import com.oyosite.ticon.lostarcana.aura.AuraSource;
import com.oyosite.ticon.lostarcana.aura.LevelAuraManager;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.Set;

@Mixin(Level.class)
public class LevelMixin implements LevelAuraManager {

    @Unique
    private final Set<AuraSource> sources = new HashSet<>();

    public @NotNull Set<@NotNull AuraSource> lostarcana$getSources() {
        return sources;
    }
}
