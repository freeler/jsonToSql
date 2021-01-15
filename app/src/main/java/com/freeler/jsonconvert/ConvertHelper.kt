package com.freeler.jsonconvert

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.*


/**
 * kml解析合并工具类
 * <coordinates>114.3684091,29.82975271,58.664</coordinates>
 * 经度lng 纬度lat 高度altitude
 */
object ConvertHelper {

    /***
     * 读取KML内容 解析
     */
    @Throws(Exception::class)
    fun parseXmlWithDom4j(context: Context, data: Intent?):String {
        val uri = data?.data ?: throw NullPointerException("加载文件失败 intent")
        val bytes = readBytes(context, uri)
        val xml = String(bytes)
        return xml
    }

    @Throws(Exception::class)
    fun readBytes(context: Context, inUri: Uri): ByteArray {
        val inputStream = context.contentResolver.openInputStream(inUri)!!
        // this dynamically extends to take the bytes you read
        val byteBuffer = ByteArrayOutputStream()
        // this is storage overwritten on each iteration with bytes
        val buffer = ByteArray(1024)
        // we need to know how may bytes were read to write them to the byteBuffer
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        // and then we can return your byte array.
        return byteBuffer.toByteArray()
    }

    @Throws(Exception::class)
    private fun parseXmlWithDom4j(xml: String) {

    }



}