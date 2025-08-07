package com.oyosite.ticon.lostarcana.item.focus

import com.oyosite.ticon.lostarcana.item.CastingItem
import com.oyosite.ticon.lostarcana.item.FOCUS_COMPONENT
import com.oyosite.ticon.lostarcana.item.FOCUS_EFFECT
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.phys.Vec3

class CastingContext {
    var castingItem: ItemStack? = null
    var focus: ItemStack? = null
    var caster: Entity? = null

    val player: Player? get() = caster as? Player

    var pos: Vec3? = null
        get() = if(field==null) caster?.position() else field

    var targetPos: Vec3? = null
    var targetBlockPos: BlockPos? = null
    var targetFace: Direction? = null

    fun tryCast(): Boolean{
        val foc = focus?:return false
        val focusEffect = foc.get(FOCUS_EFFECT)?: return false
        if(!focusEffect.isValidForContext(this)) return false
        return focusEffect.use(this)
    }

    companion object{
        operator fun invoke(useOnContext: UseOnContext): CastingContext{
            val ctx = CastingContext()
            ctx.caster = useOnContext.player
            ctx.targetPos = useOnContext.clickLocation
            ctx.targetBlockPos = useOnContext.clickedPos
            ctx.targetFace = useOnContext.clickedFace
            val stack = useOnContext.itemInHand
            val item = stack.item
            if(item is CastingItem){
                ctx.castingItem = stack
                ctx.focus = stack.get(FOCUS_COMPONENT)?.stack?.copy
            }
            return ctx
        }
    }
}