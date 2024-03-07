package edu.csus.recipedb.framework.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.net.HttpURLConnection
import java.net.URL

class HttpClient {

    companion object {

        val GSON: Gson = GsonBuilder().create()

        private val client = HttpClient()

        /**
         * Sends an HttpRequest, and returns whatever is received as a response.
         *
         * @param request request to be sent
         * @return response from the request
         */
        fun send(request: HttpRequest): HttpResponse {
            return client.sendRequest(request)
        }
    }

    fun sendRequest(request: HttpRequest): HttpResponse {
        val link = "${request.url}?${getJoinedParameters(request.headers)}"
        val connection = URL(link).openConnection() as HttpURLConnection
        connection.requestMethod = request.method.name
        for ((key, value) in request.parameters)
            connection.setRequestProperty(key, value)
        if (request.body.isNotEmpty()) {
            connection.doOutput = true
            val output = connection.outputStream
            output.write(request.body)
            output.flush()
            output.close()
        }
        val code = connection.responseCode
        val body = if (code == 204 || connection.contentLength == 0) {
            ByteArray(0)
        } else {
            connection.inputStream.readAllBytes()
        }
        return HttpResponse(code, body)
    }

    private fun getJoinedParameters(parameters: Map<String, String>): String {
        val array = arrayOfNulls<String>(parameters.size)
        var index = 0
        for ((key, value) in parameters) {
            array[index] = "$key=$value"
            index++
        }
        return array.joinToString("&")
    }
}