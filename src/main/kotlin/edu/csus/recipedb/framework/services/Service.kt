package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.logger.Logger

abstract class Service(protected val type: Type, protected val clazz: Class<*>) {

    protected val logger: Logger = Logger.getSystemLogger()

    abstract fun start(server: WebServer)

    enum class Type {
        CONTROLLER, DATABASE
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class Controller(val path: String = "")

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class Database(val url: String, val username: String = "", val password: String = "")
}
