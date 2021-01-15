package com.freeler.jsonconvert

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * T不能是Any,否则数字类型都会被解析成double
 * 只能有一层泛型，T不能是带泛型的类型
 */
inline fun <reified T> Gson.toList(json: String): ArrayList<T> {
    require(T::class.java != Any::class.java) { "Generic Type should not be Any!" }
    if (json.isEmpty()) return arrayListOf()
    return fromJson(json, GsonType(ArrayList::class.java, arrayOf(T::class.java)))
}

class GsonType(val raw: Class<*>, val args: Array<Type>? = arrayOf()) : ParameterizedType {

    override fun getRawType(): Type {
        return raw
    }

    override fun getOwnerType(): Type? {
        return null
    }

    override fun getActualTypeArguments(): Array<Type> {
        return args ?: arrayOf()
    }
}
