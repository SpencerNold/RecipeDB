package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.database.Driver
import edu.csus.recipedb.framework.translator.SystemPropertyTranslator

class DatabaseService(clazz: Class<*>, private val database: Database): Service(Type.DATABASE, clazz) {

    private val translator = SystemPropertyTranslator()

    override fun start(server: WebServer): Any? {
        val url = translator.translate(database.url)
        val username = translator.translate(database.username) ?: ""
        val password = translator.translate(database.password) ?: ""
        if (url == null || url == "") {
            logger.error("unable to connect to database: invalid url")
            return null
        }
        val instance = try {
            clazz.getDeclaredConstructor().newInstance()
        } catch (e: NoSuchMethodException) {
            logger.error("unable to start database of class: ${clazz.name}")
            return null
        }
        implement(instance, server)
        if (instance !is edu.csus.recipedb.framework.database.Database) {
            logger.error("${clazz.name} must implement the Database class in order to be registered as a service")
        }
        val driver = Driver.Factory(database.driver).create(url, username, password)
        val field = edu.csus.recipedb.framework.database.Database::class.java.getDeclaredField("driver")
        field.isAccessible = true
        field.set(instance, driver)
        (instance as edu.csus.recipedb.framework.database.Database).open()
        return instance
    }
}