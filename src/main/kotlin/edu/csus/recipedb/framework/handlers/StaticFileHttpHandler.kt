package edu.csus.recipedb.framework.handlers

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import edu.csus.recipedb.framework.logger.Logger
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.lang.reflect.Method

class StaticFileHttpHandler(private val bytes: ByteArray): Handler(), HttpHandler {

    companion object {
        private val logger = Logger.getSystemLogger()

        fun getHandler(instance: Any, method: Method): StaticFileHttpHandler {
            val bytes = if (method.parameterCount == 0) {
                method.isAccessible = true
                val response = method.invoke(instance)
                if (response != null) {
                    val input = if (response is File?) { FileInputStream(response) } else if (response is InputStream?) { response } else { null }
                    if (input != null)
                        input.readAllBytes()
                    else {
                        logger.error("${method.name} in ${instance.javaClass.name} must return an InputStream or a File to be used as a static file handler")
                        ByteArray(0)
                    }
                } else {
                    logger.error("${method.name} in ${instance.javaClass.name} returns a null page when it shouldn't")
                    ByteArray(0)
                }
            } else {
                logger.error("${method.name} in ${instance.javaClass.name} is invalid, static file handlers may not have input arguments")
                ByteArray(0)
            }
            return StaticFileHttpHandler(bytes)
        }
    }

    override fun handle(exchange: HttpExchange) {
        if (exchange.requestMethod == "GET") {
            exchange.sendResponseHeaders(200, bytes.size.toLong())
            val output = exchange.responseBody
            output.write(bytes)
            output.flush()
            output.close()
        } else {
            exchange.sendResponseHeaders(400, -1)
        }
    }
}