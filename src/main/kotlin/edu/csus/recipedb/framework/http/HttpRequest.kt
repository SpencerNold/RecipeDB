package edu.csus.recipedb.framework.http

import edu.csus.recipedb.framework.Http
import java.nio.charset.StandardCharsets

class HttpRequest(val method: Http.Method, val url: String, val parameters: Map<String, String>, val headers: Map<String, String>, val body: ByteArray) {

    class Builder(private val method: Http.Method, private val url: String, private var parameters: Map<String, String> = mapOf(), private var headers: Map<String, String> = mapOf(), private var body: ByteArray = ByteArray(0)) {

        fun parameters(parameters: Map<String, String>) = apply { this.parameters = parameters }
        fun headers(headers: Map<String, String>) = apply { this.headers = headers }
        fun body(body: ByteArray) = apply { this.body = body }
        fun body(body: String) = apply { this.body = body.toByteArray(StandardCharsets.UTF_8) }
        fun body(body: Any) = apply { this.body = HttpClient.GSON.toJson(body).toByteArray(StandardCharsets.UTF_8) }

        fun build(): HttpRequest {
            return HttpRequest(method, url, parameters, headers, body)
        }
    }
}