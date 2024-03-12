package edu.csus.recipedb.framework.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets

open class HttpResponse(val code: Int, val body: ByteArray) {

    private val gson: Gson = GsonBuilder().create()

    fun getBodyAsString(): String {
        return String(body, StandardCharsets.UTF_8)
    }

    fun getBodyFromJson(type: Type) {
        return gson.fromJson(getBodyAsString(), type)
    }
}