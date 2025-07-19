package com.oyosite.ticon.lostarcana.client.entity

import com.mojang.blaze3d.vertex.PoseStack


inline operator fun PoseStack.invoke(block: PoseStack.()->Unit) {
    pushPose()
    block()
    popPose()
}