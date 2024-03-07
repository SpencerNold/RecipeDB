package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.database.Driver
import edu.csus.recipedb.framework.logger.Logger

abstract class Service(protected val type: Type, protected val clazz: Class<*>) {

    protected val logger: Logger = Logger.getSystemLogger()

    abstract fun start(server: WebServer): Any?

    protected fun implement(instance: Any, server: WebServer) {
        if (instance !is Implementation)
            return
        val field = Implementation::class.java.getDeclaredField("server")
        field.isAccessible = true
        field.set(instance, server)
    }

    enum class Type {
        CONTROLLER, DATABASE
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class Controller(val path: String = "")

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.CLASS)
    annotation class Database(val driver: Driver.Type, val url: String, val permission: String = "", val username: String = "", val password: String = "")
}
