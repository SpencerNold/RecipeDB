package edu.csus.recipedb.framework

import edu.csus.recipedb.framework.handlers.Handler
import edu.csus.recipedb.framework.logger.Logger
import edu.csus.recipedb.framework.services.ControllerService
import edu.csus.recipedb.framework.services.DatabaseService
import edu.csus.recipedb.framework.services.Service
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class WebServer(val port: Int) {

    /**
     * Starts the web server on the port input in the constructor.
     */
    abstract fun start()

    /**
     * Stops the web server, a clean shutdown method.
     */
    abstract fun stop()

    /**
     * Reloads the web server, does not shut the server down and completely restart it, only reloads all registered handlers.
     */
    abstract fun reload()

    /**
     * Registers a handler to the web server.
     *
     * @param path the path, appended to the website url to serve a handler
     * @param handler handles what is served to the path
     */
    abstract fun addHandler(path: String, handler: Handler)

    /**
     * Gets an instance of the internal server object.
     */
    abstract fun getInternalServerObject(): Any

    /**
     * Registers a service to the web server to handle logic.
     *
     * @param service service to be added to the web server
     */
    abstract fun register(service: Any)

    /**
     * Gets a service of type <T> which has been registered to the web server.
     *
     * @param clazz class of the server being registered
     */
    abstract fun <T> service(clazz: Class<T>): T?

    /**
     * Part of the Builder design pattern, used to create an instance of a web server.
     *
     * @param protocol the protocol to be used
     * @param port port to open the web server on
     */
    class Builder(
        val protocol: Protocol,
        val port: Int,
        var services: Array<Class<*>> = arrayOf(),
        var executor: ExecutorService? = null,
    ) {

        /**
         * Registers services to the web server. These include controllers, and database services, which are responsible for the logic of the server.
         *
         * @param services classes of the service implementations, they must always include service type annotation
         */
        fun services(vararg services: Class<*>) = apply { this.services = arrayOf(*services) }

        /**
         * Sets an ExecutorService for the web server to use. By default, the server uses a single threaded executor.
         *
         * @param executor executor instance to be used by the server
         */
        fun executor(executor: ExecutorService) = apply { this.executor = executor }

        /**
         * Builds the web server. Services are initialized by this method.
         */
        fun build(): WebServer {
            return Factory(this).create()
        }
    }

    private data class Factory(private val builder: Builder) {

        private val logger = Logger.getSystemLogger()

        fun create(): WebServer {
            val server = when (builder.protocol) {
                Protocol.HTTP -> HttpWebServerImpl(
                    builder.port,
                    builder.executor ?: Executors.newSingleThreadExecutor()
                )
            }
            for (clazz in builder.services) {
                val instance = if (clazz.isAnnotationPresent(Service.Controller::class.java)) {
                    val controller = clazz.getAnnotation(Service.Controller::class.java)
                    val service = ControllerService(clazz, controller)
                    service.start(server)
                } else if (clazz.isAnnotationPresent(Service.Database::class.java)) {
                    val database = clazz.getAnnotation(Service.Database::class.java)
                    val service = DatabaseService(clazz, database)
                    service.start(server)
                } else {
                    logger.error("${clazz.name} must have a service annotation to be used as one")
                    null
                }
                if (instance != null)
                    server.register(instance)
            }
            return server
        }
    }
}