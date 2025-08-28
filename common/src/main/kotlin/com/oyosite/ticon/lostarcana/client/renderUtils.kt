package com.oyosite.ticon.lostarcana.client

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer



fun drawArbitraryCuboid(poseStack: PoseStack, buffer: VertexConsumer, color: Int, light: Int, overlay: Int, upUV: FloatArray, downUV: FloatArray, side1UV: FloatArray, side2UV: FloatArray, side3UV: FloatArray, side4UV: FloatArray, x: Float, y: Float, z: Float, width: Float, height: Float, depth: Float){
    poseStack.pushPose()
    poseStack.translate(x, y, z)
    val pose = poseStack.last()
    drawFace(poseStack.last(), buffer, color, light, overlay, upUV[0], upUV[1], upUV[2], upUV[3], floatArrayOf(
        0f,height,0f,
        0f,height,depth,
        width,height,depth,
        width,height,0f,
    ))
    drawFace(poseStack.last(), buffer, color, light, overlay, downUV[0], downUV[1], downUV[2], downUV[3], floatArrayOf(
        width,0f,0f,
        width,0f,depth,
        0f,0f,depth,
        0f,0f,0f,
    ))

    drawFace(poseStack.last(), buffer, color, light, overlay, side1UV[0], side1UV[1], side1UV[2], side1UV[3], floatArrayOf(
        0f, 0f, depth,
        0f, height, depth,
        0f, height, 0f,
        0f, 0f, 0f,
    ))
    drawFace(poseStack.last(), buffer, color, light, overlay, side2UV[0], side2UV[1], side2UV[2], side2UV[3], floatArrayOf(
        width, 0f, 0f,
        width, height, 0f,
        width, height, depth,
        width, 0f, depth,
    ))
    drawFace(poseStack.last(), buffer, color, light, overlay, side3UV[0], side3UV[1], side3UV[2], side3UV[3], floatArrayOf(
        0f, 0f, 0f,
        0f, height, 0f,
        width, height, 0f,
        width, 0f, 0f,
    ))
    drawFace(poseStack.last(), buffer, color, light, overlay, side4UV[0], side4UV[1], side4UV[2], side4UV[3], floatArrayOf(
        width, 0f, depth,
        width, height, depth,
        .0f, height, depth,
        .0f, 0f, depth,
    ))

    poseStack.popPose()
}

fun drawFace(pose: PoseStack.Pose, buffer: VertexConsumer, color: Int, light: Int, overlay: Int, minU: Float, minV: Float, maxU: Float, maxV: Float, coords: FloatArray){
    buffer.addVertex(pose, coords[0], coords[1], coords[2])
        .setColor(color)
        .setUv(minU, maxV)
        .setOverlay(overlay)
        .setLight(light)
        .setNormal(1f,1f,1f)
    buffer.addVertex(pose, coords[3], coords[4], coords[5])
        .setColor(color)
        .setUv(minU, minV)
        .setOverlay(overlay)
        .setLight(light)
        .setNormal(1f,1f,1f)
    buffer.addVertex(pose, coords[6], coords[7], coords[8])
        .setColor(color)
        .setUv(maxU, minV)
        .setOverlay(overlay)
        .setLight(light)
        .setNormal(1f,1f,1f)
    buffer.addVertex(pose, coords[9], coords[10], coords[11])
        .setColor(color)
        .setUv(maxU, maxV)
        .setOverlay(overlay)
        .setLight(light)
        .setNormal(1f,1f,1f)
}