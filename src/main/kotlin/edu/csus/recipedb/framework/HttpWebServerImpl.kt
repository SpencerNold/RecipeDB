package edu.csus.recipedb.framework

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import edu.csus.recipedb.framework.handlers.Handler
import edu.csus.recipedb.framework.logger.Logger
import java.net.InetSocketAddress
import java.util.concurrent.ExecutorService

class HttpWebServerImpl(port: Int, private val executor: ExecutorService): WebServer(port) {

    private val logger: Logger = Logger.getSystemLogger()
    private val server: HttpServer = HttpServer.create(InetSocketAddress(port), 0)
    private val handlers: MutableMap<String, Handler> = mutableMapOf()
    private val services: MutableMap<Class<*>, Any> = mutableMapOf()
    private var running: Boolean = false

    override fun start() {
        if (running)
            return
        running = true
        logger.info("Starting server on port: $port")
        server.executor = executor
        server.start()
    }

    override fun stop() {
        if (!running)
            return
        running = false
        logger.info("Shutting down server...")
        server.stop(0)
    }

    override fun reload() {
        TODO("Not implemented yet!")
    }

    override fun addHandler(path: String, handler: Handler) {
        if (handler !is HttpHandler) {
            logger.warn("Unable to create handler at path \"$path\"")
            return
        }
        server.createContext(path, handler as HttpHandler)
        handlers[path] = handler
        logger.info("Added handler at path \"$path\"")
    }

    override fun getInternalServerObject(): Any {
        return server
    }

    override fun register(service: Any) {
        services[service.javaClass] = service
    }

    override fun <T> service(clazz: Class<T>): T? {
        val service = services[clazz]
        return if (service == null) {
            logger.error("no such service of type: ${clazz.name}")
            null
        } else if (!clazz.isInstance(service)) {
            logger.error("no such service of type: ${clazz.name}")
            null
        } else {
            clazz.cast(service)
        }
    }
}