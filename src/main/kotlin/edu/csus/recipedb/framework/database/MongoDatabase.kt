package edu.csus.recipedb.framework.database

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement

abstract class MongoDatabase: Database() {

    private val gson = GsonBuilder().create()

    override fun open() {
        getDriver(MongoDriver::class.java).init()
    }

    /**
     * Sends a json statement to the MongoDB driver, and returns whatever the response is as a json string.
     *
     * @param json a json string request
     * @return json string response
     */
    protected fun execute(json: String): String {
        return getDriver(MongoDriver::class.java).execute(json)
    }

    /**
     * Sends a json statement to the MongoDB driver. The request is serialized into json based on it's field names and values, and the response is deserialized the same way with the response type parameter.
     *
     * @param response type of class to deserialize into
     * @param request request object being sent
     * @return instance of the deserialized object
     */
    protected fun <T> execute(response: Class<T>, request: Any): T {
        var json = gson.toJson(request)
        json = execute(json)
        return gson.fromJson(json, response)
    }

    protected fun <T> execute(response: Class<T>, request: String): T {
        return gson.fromJson(execute(request), response)
    }

    /**
     * Switches the MongoDB connection to the "admin" database
     */
    protected fun setAdminMode() {
        getDriver(MongoDriver::class.java).setToAdmin()
    }

    /**
     * Switches the MongoDB connection to the "local" database
     */
    protected fun setUserMode() {
        getDriver(MongoDriver::class.java).setToLocal()
    }
}