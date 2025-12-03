package com.oyosite.ticon.lostarcana.client.fabric


/*Based on Botania code*/
interface ExtendedTexture{
    fun `lostarcana$setFilterSave`(bilinear: Boolean, mipmap: Boolean)

    fun `lostarcana$restoreLastFilter`()
}