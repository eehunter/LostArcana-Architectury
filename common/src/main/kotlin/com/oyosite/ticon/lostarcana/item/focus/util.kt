package com.oyosite.ticon.lostarcana.item.focus

import com.oyosite.ticon.lostarcana.util.platformRegisterCastingFocusEffectType


operator fun <T: CastingFocusEffect> CastingFocusEffectType<T>.invoke(name: String) =
    platformRegisterCastingFocusEffectType(name){this}

fun registerBuiltinEffectTypes(){
    CastingFocusEffect.NONE.type("none")
    VisLightEffect.Serializer("light")
}