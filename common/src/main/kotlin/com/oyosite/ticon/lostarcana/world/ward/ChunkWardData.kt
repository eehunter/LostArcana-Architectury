package com.oyosite.ticon.lostarcana.world.ward

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import java.util.UUID

class ChunkWardData(val worldHeight: Int, val minY: Int) {

    val data = ByteArray(16*16*worldHeight){ 0 }
    val wardIds = mutableMapOf<Byte, UUID>()

    var isDirty = true

    private var _compressedData: ByteArray = byteArrayOf()
    val compressedDataList get() = compressedData.toList()
    val compressedData: ByteArray get() {
        if(isDirty) compressForSave()
        return _compressedData
    }
    private var _indexStart: Int = 0
    val indexStart: Int get() {
        if(isDirty) compressForSave()
        return _indexStart
    }
    private var _indexEnd: Int = 0
    val indexEnd: Int get() {
        if(isDirty) compressForSave()
        return _indexEnd
    }
    private var _persistentIds = mutableMapOf<Byte, String>()
    val persistentIds: Map<Byte, String> get(){
        if(isDirty) compressForSave()
        return _persistentIds
    }


    private fun compressForSave(){
        _indexStart = data.indexOfFirst { it != 0.toByte() }
        _indexEnd = data.indexOfLast { it != 0.toByte() }
        _compressedData = ByteArray(_indexEnd-_indexStart) { data[_indexStart+it] }
        val remainingIds = data.toSet()
        _persistentIds.clear()
        wardIds.keys.filter { it in remainingIds }.forEach { _persistentIds[it] = wardIds[it].toString() }
        isDirty = false
    }


    fun isWarded(pos: BlockPos, uuid: UUID? = null): Boolean{
        val x = pos.x%16
        val y = pos.y - minY
        val z = pos.z%16
        return data[x + z*16 + y*256] != 0.toByte()
    }

    fun addWard(pos: BlockPos, uuid: UUID): Boolean{
        isDirty = true
        var wardID = wardIds.entries.firstOrNull { it.value == uuid }?.key?:0
        if(wardID == 0.toByte()){
            if(wardIds.size >= 255)return false
            wardID = (1..255).first{ !wardIds.contains(it.toByte()) }.toByte()
            wardIds[wardID] = uuid
        }
        val x = pos.x%16
        val y = pos.y - minY
        val z = pos.z%16
        data[x + z*16 + y*256] = wardID
        return true
    }

    fun removeWard(pos: BlockPos, uuid: UUID? = null): Boolean{
        isDirty = true
        val x = pos.x%16
        val y = pos.y - minY
        val z = pos.z%16
        if(data[x + z*16 + y*256] == 0.toByte()) return false
        if(uuid == null){
            data[x + z*16 + y*256] = 0
            return true
        }
        var wardID = wardIds.entries.firstOrNull { it.value == uuid }?.key?:0
        if(data[x + z*16 + y*256] == wardID){
            data[x + z*16 + y*256] = 0
            return true
        }
        return false
    }

    fun clearWards(uuid: UUID? = null){
        isDirty = true
        if(uuid == null){
            data.indices.forEach { data[it] = 0 }
            wardIds.clear()
            return
        }
        val wardID = wardIds.entries.firstOrNull { it.value == uuid }?.key?:0
        if(wardID == 0.toByte()) return
        data.indices.forEach { if(data[it] == wardID) data[it] = 0 }
        wardIds.remove(wardID)
    }

    fun codec(): Codec<ChunkWardData> = CODEC
    fun streamCodec(): StreamCodec<ByteBuf, ChunkWardData> = STREAM_CODEC

    companion object{
        val CODEC: Codec<ChunkWardData> = RecordCodecBuilder.create { it.group(
            Codec.INT.fieldOf("worldHeight").forGetter(ChunkWardData::worldHeight),
            Codec.INT.fieldOf("minY").forGetter(ChunkWardData::worldHeight),
            Codec.INT.fieldOf("start").forGetter(ChunkWardData::indexStart),
            Codec.INT.fieldOf("end").forGetter(ChunkWardData::indexEnd),
            Codec.list(Codec.BYTE).fieldOf("data").forGetter(ChunkWardData::compressedDataList),
            Codec.unboundedMap(Codec.BYTE, Codec.STRING).fieldOf("ids").forGetter(ChunkWardData::persistentIds)
            ).apply(it, ::decompress)
        }

        fun decompress(worldHeight: Int, minY: Int, start: Int, end: Int, data: ByteArray, ids: Map<Byte, String>): ChunkWardData =
            decompress(worldHeight, minY, start, end, data.toList(), ids)
        fun decompress(worldHeight: Int, minY: Int, start: Int, end: Int, data: List<Byte>, ids: Map<Byte, String>): ChunkWardData{
            val dat = ChunkWardData(worldHeight, minY)
            if(start == -1) return dat
            (start..end).forEachIndexed { i, it ->
                dat.data[it] = data[i]
            }
            ids.forEach {
                dat.wardIds[it.key] = UUID.fromString(it.value)
            }
            return dat
        }

        val STREAM_CODEC: StreamCodec<ByteBuf, ChunkWardData> = StreamCodec.composite(
            ByteBufCodecs.INT, ChunkWardData::worldHeight,
            ByteBufCodecs.INT, ChunkWardData::minY,
            ByteBufCodecs.INT, ChunkWardData::indexStart,
            ByteBufCodecs.INT, ChunkWardData::indexEnd,
            ByteBufCodecs.BYTE_ARRAY, ChunkWardData::compressedData,
            ByteBufCodecs.map({mutableMapOf<Byte, String>()},
                ByteBufCodecs.BYTE,
                ByteBufCodecs.STRING_UTF8
            ), ChunkWardData::persistentIds,
            ::decompress
        )
    }
}