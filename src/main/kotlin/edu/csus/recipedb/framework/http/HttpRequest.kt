package edu.csus.recipedb.framework.http

import edu.csus.recipedb.framework.Http

class HttpRequest(val url: String, val method: Http.Method, val parameters: Map<String, String>, val headers: Map<String, String>, val body: ByteArray)