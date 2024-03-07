package edu.csus.recipedb.framework.database

import com.google.gson.GsonBuilder

abstract class MongoDatabase: Database() {

    private val gson = GsonBuilder().create()

    override fun open() {
        getDriver(MongoDriver::class.java).init()
    }

    fun execute(json: String): String {
        return getDriver(MongoDriver::class.java).execute(json)
    }

    fun <T> execute(response: Class<T>, request: Any): T {
        var json = gson.toJson(request)
        json = execute(json)
        return gson.fromJson(json, response)
    }

    fun setAdminMode() {
        getDriver(MongoDriver::class.java).setToAdmin()
    }

    fun setUserMode() {
        getDriver(MongoDriver::class.java).setToLocal()
    }
}