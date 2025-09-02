package com.oyosite.ticon.lostarcana.neoforge.block

import com.google.common.base.MoreObjects
import com.oyosite.ticon.lostarcana.item.RAW_ASPECT_COMPONENT
import dev.architectury.core.fluid.ArchitecturyFluidAttributes
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge
import net.minecraft.Util
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.material.FluidState
import net.neoforged.neoforge.common.SoundAction
import net.neoforged.neoforge.fluids.FluidStack
import net.neoforged.neoforge.fluids.FluidType

class EssentiaFluidType(properties: Properties, val attributes: ArchitecturyFluidAttributes) : FluidType(properties.addArchProperties(attributes)) {

    val defaultTranslationKey: String = Util.makeDescriptionId("fluid", BuiltInRegistries.FLUID.getKey(attributes.sourceFluid))

    override fun getBucket(stack: FluidStack): ItemStack {
        return super.getBucket(stack).also { it[RAW_ASPECT_COMPONENT] = stack[RAW_ASPECT_COMPONENT] }
    }

    override fun getLightLevel(stack: FluidStack): Int {
        return this.attributes.getLuminosity(this.convertSafe(stack))
    }

    override fun getLightLevel(state: FluidState, level: BlockAndTintGetter, pos: BlockPos): Int {
        return this.attributes.getLuminosity(this.convertSafe(state), level, pos)
    }

    override fun getDensity(stack: FluidStack): Int {
        return this.attributes.getDensity(this.convertSafe(stack))
    }

    override fun getDensity(state: FluidState, level: BlockAndTintGetter, pos: BlockPos): Int {
        return this.attributes.getDensity(this.convertSafe(state), level, pos)
    }

    override fun getTemperature(stack: FluidStack): Int {
        return this.attributes.getTemperature(this.convertSafe(stack))
    }

    override fun getTemperature(state: FluidState, level: BlockAndTintGetter, pos: BlockPos): Int {
        return this.attributes.getTemperature(this.convertSafe(state), level, pos)
    }

    override fun getViscosity(stack: FluidStack): Int {
        return this.attributes.getViscosity(this.convertSafe(stack))
    }

    override fun getViscosity(state: FluidState, level: BlockAndTintGetter, pos: BlockPos): Int {
        return this.attributes.getViscosity(this.convertSafe(state), level, pos)
    }

    override fun getRarity(): Rarity {
        return this.attributes.getRarity()
    }

    override fun getRarity(stack: FluidStack): Rarity {
        return this.attributes.getRarity(this.convertSafe(stack))
    }

    override fun getDescription(): Component {
        return this.attributes.getName()
    }

    override fun getDescription(stack: FluidStack): Component {
        return this.attributes.getName(this.convertSafe(stack))
    }

    override fun getDescriptionId(): String {
        return MoreObjects.firstNonNull<String?>(
            this.attributes.translationKey,
            this.defaultTranslationKey
        ) as String
    }

    override fun getDescriptionId(stack: FluidStack): String {
        return MoreObjects.firstNonNull<String?>(
            this.attributes.getTranslationKey(this.convertSafe(stack)),
            this.defaultTranslationKey
        ) as String
    }

    override fun getSound(action: SoundAction): SoundEvent? {
        return this.getSound(null as FluidStack?, action)
    }

    override fun getSound(stack: FluidStack?, action: SoundAction): SoundEvent? {
        val archStack: dev.architectury.fluid.FluidStack? = this.convertSafe(stack)
        if (SoundEvents.BUCKET_FILL == action) {
            return this.attributes.getFillSound(archStack)
        } else {
            return if (SoundEvents.BUCKET_EMPTY == action) this.attributes.getEmptySound(archStack) else null
        }
    }

    override fun getSound(player: Player?, getter: BlockGetter, pos: BlockPos, action: SoundAction): SoundEvent? {
        if (getter is BlockAndTintGetter) {
            if (SoundEvents.BUCKET_FILL == action) {
                return this.attributes.getFillSound(null as dev.architectury.fluid.FluidStack?, getter, pos)
            }

            if (SoundEvents.BUCKET_EMPTY == action) {
                return this.attributes.getEmptySound(null as dev.architectury.fluid.FluidStack?, getter, pos)
            }
        }

        return this.getSound(null as FluidStack?, action)
    }

    override fun canConvertToSource(stack: FluidStack): Boolean {
        return this.attributes.canConvertToSource()
    }

    override fun canConvertToSource(state: FluidState, reader: LevelReader, pos: BlockPos): Boolean {
        return this.attributes.canConvertToSource()
    }


    companion object{
        private fun Properties.addArchProperties(attributes: ArchitecturyFluidAttributes): Properties =
            lightLevel(attributes.luminosity).density(attributes.density)
                .temperature(attributes.temperature).rarity(attributes.rarity)
                .canConvertToSource(attributes.canConvertToSource()).viscosity(attributes.viscosity)
    }

    fun convertSafe(stack: FluidStack?): ArchFluidStack? {
        return stack?.let(FluidStackHooksForge::fromForge)//if (stack == null) null else FluidStackHooksForge.fromForge(stack)
    }

    fun convertSafe(state: FluidState?): ArchFluidStack? {
        return if (state == null) null else ArchFluidStack.create(state.type, ArchFluidStack.bucketAmount())
    }
}