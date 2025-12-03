package com.oyosite.ticon.lostarcana.client.fx

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
import com.oyosite.ticon.lostarcana.LostArcana
import com.oyosite.ticon.lostarcana.canSeeAuraNode
import com.oyosite.ticon.lostarcana.client.restoreLastFilter
import com.oyosite.ticon.lostarcana.client.setFilterSave
import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.particle.TextureSheetParticle
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.renderer.texture.TextureManager
import org.lwjgl.opengl.GL11
import java.util.function.Supplier


/*Based on Botania code*/
class FXWisp(
    level: ClientLevel,
    d: Double, d1: Double, d2: Double,
    xSpeed: Double,ySpeed: Double, zSpeed: Double,
    pSize: Float,
    red: Float, green: Float, blue: Float, alpha: Float,
    val depthTest: Boolean, maxAgeMultiplier: Float,
    noClip: Boolean, pGravity: Float,
    val needsRevealing: Boolean
) : TextureSheetParticle(level, d, d1, d2) {

    init {
        xd = xSpeed
        yd = ySpeed
        zd = zSpeed
        rCol = red
        gCol = green
        bCol = blue
        this.alpha = alpha//alpha = 0.375F

        gravity = pGravity

        setSize(0.01f,0.01f)

        xo = x
        yo = y
        zo = z

        hasPhysics = !noClip

        lifetime = (28.0 / (Math.random() * 0.3 + 0.7) * maxAgeMultiplier).toInt()
    }


    init {
        quadSize = (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F * pSize
    }
    val moteParticleScale = quadSize
    val moteHalfLife = lifetime / 2

    override fun getQuadSize(p_217561_1_: Float): Float {
        var agescale = age.toFloat() / moteHalfLife
        if (agescale > 1f) {
            agescale = 2 - agescale
        }

        quadSize = moteParticleScale * agescale * 0.5f
        return quadSize
    }

    override fun tick() {
        this.xo = this.x
        this.yo = this.y
        this.zo = this.z

        if (this.age++ >= this.lifetime) {
            this.remove()
        }

        this.yd -= this.gravity.toDouble()
        this.move(this.xd, this.yd, this.zd)
        if (gravity == 0.0f) {
            this.xd *= 0.98
            this.yd *= 0.98
            this.zd *= 0.98
        }
    }

    override fun render(vertexConsumer: VertexConsumer, camera: Camera, f: Float) {
        if(needsRevealing){
            if(Minecraft.getInstance().player?.canSeeAuraNode == false) return
        }
        super.render(vertexConsumer, camera, f)
    }

    override fun getRenderType(): ParticleRenderType = //ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT//
        (if(depthTest) NORMAL_RENDER else DIW_RENDER).also{ println("Fetching render type") }

    companion object{
        private var testFlag = true
        private fun beginRenderCommon(tesselator: Tesselator, textureManager: TextureManager): BufferBuilder {
            Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer()
            RenderSystem.depthMask(false)
            RenderSystem.enableBlend()
            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE)

            if(testFlag){
                println("Beginning particle render")
                testFlag = false
            }
            RenderSystem.setShader { GameRenderer.getParticleShader() }

            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES)
            val tex = textureManager.getTexture(TextureAtlas.LOCATION_PARTICLES)
            //ClientXplatAbstractions.INSTANCE.setFilterSave(tex, true, false)
            setFilterSave(tex, filter = true, mipmap = false)
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE)
        }

        private fun endRenderCommon() {
            val tex = Minecraft.getInstance().getTextureManager().getTexture(TextureAtlas.LOCATION_PARTICLES)
            //ClientXplatAbstractions.INSTANCE.restoreLastFilter(tex)
            restoreLastFilter(tex)
            RenderSystem.disableBlend()
            RenderSystem.depthMask(true)
        }

        val NORMAL_RENDER: ParticleRenderType = object : LostArcanaParticleRenderType {
            override fun begin(tesselator: Tesselator, textureManager: TextureManager): BufferBuilder {
                RenderSystem.enableDepthTest()
                return beginRenderCommon(tesselator, textureManager)
            }

            override fun end() {
                endRenderCommon()
            }

            override fun toString(): String {
                return "${LostArcana.MOD_ID}:wisp"
            }
        }

        val DIW_RENDER: ParticleRenderType = object : LostArcanaParticleRenderType {
            override fun begin(tesselator: Tesselator, textureManager: TextureManager): BufferBuilder {
                RenderSystem.disableDepthTest()
                return beginRenderCommon(tesselator, textureManager)
            }

            override fun end() {
                RenderSystem.enableDepthTest()
                endRenderCommon()
            }

            override fun toString(): String {
                return "${LostArcana.MOD_ID}:depth_ignoring_wisp"
            }
        }
    }
}