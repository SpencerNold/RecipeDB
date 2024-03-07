package edu.csus.recipedb.framework.http

import edu.csus.recipedb.framework.Http
import java.nio.charset.StandardCharsets

/**
 * Creates an object representation of a request in the hyper-text transfer protocol to be sent.
 *
 * @param method method of the request
 * @param url url to send the request to
 * @param parameters query parameters of the request
 * @param headers headers of the request
 * @param body body of the request
 */
class HttpRequest(val method: Http.Method, val url: String, val parameters: Map<String, String>, val headers: Map<String, String>, val body: ByteArray) {

    /**
     * Follows the Builder design pattern to create an Http request.
     *
     * @param method method of the http request
     * @param url url to send the request to
     */
    class Builder(private val method: Http.Method, private val url: String, private var parameters: Map<String, String> = mapOf(), private var headers: Map<String, String> = mapOf(), private var body: ByteArray = ByteArray(0)) {

        /**
         * Sets the parameters of the request as query parameters.
         *
         * @param parameters query parameters of the request
         */
        fun parameters(parameters: Map<String, String>) = apply { this.parameters = parameters }

        /**
         * Sets Http headers in the request. These can update default headers, or be added as new headers.
         *
         * @param headers headers of the Http request
         */
        fun headers(headers: Map<String, String>) = apply { this.headers = headers }

        /**
         * Sets the body of the request.
         *
         * @param body body of the request
         */
        fun body(body: ByteArray) = apply { this.body = body }

        /**
         * Sets the body of the request to a UTF-8 encoded string.
         *
         * @param body body of the request, to be encoded into a UTF-8 byte array
         */
        fun body(body: String) = apply { this.body = body.toByteArray(StandardCharsets.UTF_8) }

        /**
         * Sets the body of the request to an object encoded into json.
         *
         * @param body body of the request, is turned into json, then into bytes
         */
        fun body(body: Any) = apply { this.body = HttpClient.GSON.toJson(body).toByteArray(StandardCharsets.UTF_8) }

        /**
         * Builds the Http request.
         */
        fun build(): HttpRequest {
            return HttpRequest(method, url, parameters, headers, body)
        }
    }
}