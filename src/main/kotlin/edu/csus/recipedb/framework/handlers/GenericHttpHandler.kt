package edu.csus.recipedb.framework.handlers

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import edu.csus.recipedb.framework.Http
import edu.csus.recipedb.framework.Route
import edu.csus.recipedb.framework.exceptions.HandlerException
import edu.csus.recipedb.framework.http.HttpRequest
import edu.csus.recipedb.framework.http.HttpResponse
import java.lang.reflect.Method
import java.nio.charset.StandardCharsets

class GenericHttpHandler(private val route: Route, private val instance: Any, private val method: Method): Handler(), HttpHandler {

    private val gson: Gson = GsonBuilder().create()

    override fun handle(exchange: HttpExchange) {
        if (exchange.requestMethod == route.method.name) {
            val body = exchange.requestBody.readAllBytes()
            if (body.isEmpty() == route.input) {
                exchange.sendResponseHeaders(400, -1)
                return
            }
            if (route.input) {
                if (method.parameterCount == 0)
                    throw HandlerException("@Route is expecting input, but no parameters to the ${method.name} are given")
            } else {
                if (method.parameterCount != 0)
                    throw HandlerException("@Route is not expecting input, but parameters to the ${method.name} exist")
            }
            method.isAccessible = true
            val response: Any? = if (route.input) {
                val parameter = method.parameters[0]
                val argument: Any? = if (parameter.type == HttpRequest::class.java) {
                    val uri = exchange.requestURI.toASCIIString().split("?")
                    val headers = mutableMapOf<String, String>()
                    for (key in exchange.requestHeaders.keys)
                        headers[key] = exchange.requestHeaders.getValue(key)[0]
                    val parameters = mutableMapOf<String, String>()
                    for (str in uri[1].split("&")) {
                        val s = str.split("=")
                        parameters[s[0]] = s[1]
                    }
                    HttpRequest(route.method, uri[0], parameters, headers, body)
                } else {
                    gson.fromJson(String(body), parameter.type)
                }
                method.invoke(instance, argument)
            } else {
                method.invoke(instance)
            }
            if (response == null) {
                exchange.sendResponseHeaders(204, -1)
                return
            }
            val bytes = if (response is HttpResponse) { response.body } else { gson.toJson(response).toByteArray(charset = StandardCharsets.UTF_8) }
            if (bytes.isEmpty()) {
                exchange.sendResponseHeaders(if (response is HttpResponse) { response.code } else { 204 }, -1)
                return
            }
            exchange.sendResponseHeaders(if (response is HttpResponse) { response.code } else { 200 }, bytes.size.toLong())
            exchange.responseBody.write(bytes)
        } else {
            exchange.sendResponseHeaders(400, -1)
        }
    }
}