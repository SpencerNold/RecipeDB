package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.database.Database
import java.sql.Connection
import java.sql.DriverManager

class DatabaseService(clazz: Class<*>, private val database: Database): Service(Type.DATABASE, clazz) {

    override fun start(server: WebServer) {
        // TODO Temporary
        var url = database.url
        val connection = DriverManager.getConnection(database.url, database.username, database.password)
        val instance = try {
            clazz.getDeclaredConstructor().newInstance()
        } catch (e: NoSuchMethodException) {
            logger.error("Unable to start database of class: ${clazz.name}")
            return
        }
        if (instance !is edu.csus.recipedb.framework.database.Database) {
            logger.error("${clazz.name} does not extend the Database class and can't be used as a database service")
            return
        }
        val method = edu.csus.recipedb.framework.database.Database::class.java.getDeclaredMethod("init", Connection::class.java)
        method.isAccessible = true
        method.invoke(instance, connection)
    }
}