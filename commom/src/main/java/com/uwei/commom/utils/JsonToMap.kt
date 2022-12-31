package com.uwei.commom.utils

import com.google.gson.*
import com.uwei.commom.utils.JsonToMap
import java.util.ArrayList
import java.util.HashMap

/**
 * @Author Charlie
 * @Date 2022/7/21 17:03
 */
object JsonToMap {

    /**
     * 依据json返回Map
     * @return
     */
    fun toMap(json: String): Map<String, Any> {
        return toMap(parseJson(json))
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * @param jsonObject
     * @return
     */
    fun toMap(jsonObject: JsonObject): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        val entrySet = jsonObject.entrySet()
        val iter: Iterator<Map.Entry<String, JsonElement>> = entrySet.iterator()
        while (iter.hasNext()) {
            val (key, value) = iter.next()
            if (value is JsonArray) {
                map[key] = toList(value)
            } else if (value is JsonObject) {
                map[key] = toMap(value)
            } else {
                map[key] = value
            }
        }
        return map
    }

    /**
     * 将JSONArray对象转换成List集合
     * @param json
     * @return
     */
    fun toList(json: JsonArray): List<Any> {
        val list: MutableList<Any> = ArrayList()
        for (i in 0 until json.size()) {
            val value: Any = json[i]
            if (value is JsonArray) {
                list.add(toList(value))
            } else if (value is JsonObject) {
                list.add(toMap(value))
            } else {
                list.add(value)
            }
        }
        return list
    }

    /**
     * 获取JsonObject
     * @param json
     * @return
     */
    private fun parseJson(json: String): JsonObject {
        val parser = JsonParser()
        return parser.parse(json).asJsonObject
    }

    private fun parseArray(json: String): JsonArray {
        val parser = JsonParser()
        return parser.parse(json).asJsonArray
    }

    /**
     * Map转Json
     */
    fun toJson(hashMap: HashMap<*,*>): String{
        val jsonObject = GsonBuilder().create().toJson(hashMap)
        return "$jsonObject"
    }

}