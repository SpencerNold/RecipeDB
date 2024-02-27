package edu.csus.recipedb.framework

import edu.csus.recipedb.framework.handlers.Handler
import edu.csus.recipedb.framework.services.ControllerService
import edu.csus.recipedb.framework.services.DatabaseService
import edu.csus.recipedb.framework.services.Service
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class WebServer(val port: Int) {
    abstract fun start()
    abstract fun stop()
    abstract fun reload()
    abstract fun addHandler(path: String, handler: Handler)
    abstract fun getInternalServerObject(): Any

    class Builder(var protocol: Protocol, val port: Int, var services: Array<Class<*>> = arrayOf(), var executor: ExecutorService? = null, var arguments: Array<String> = arrayOf()) {
        fun services(vararg services: Class<*>) = apply { this.services = arrayOf(*services) }
        fun executor(executor: ExecutorService) = apply { this.executor = executor }
        fun arguments(arguments: Array<String>) = apply { this.arguments = arguments }
        fun build(): WebServer {
            return Factory(this).create()
        }
    }

    private data class Factory(private val builder: Builder) {
        fun create(): WebServer {
            val server = when (builder.protocol) {
                Protocol.HTTP -> HttpWebServerImpl(builder.port, builder.executor ?: Executors.newSingleThreadExecutor())
            }
            for (clazz in builder.services) {
                if (clazz.isAnnotationPresent(Service.Controller::class.java)) {
                    val controller = clazz.getAnnotation(Service.Controller::class.java)
                    val service = ControllerService(clazz, controller)
                    service.start(server)
                } else if (clazz.isAnnotationPresent(Service.Database::class.java)) {
                    val database = clazz.getAnnotation(Service.Database::class.java)
                    val service = DatabaseService(clazz, database)
                    service.start(server)
                } else {
                    TODO("Throw an exception")
                }
            }
            return server
        }
    }
}