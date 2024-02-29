package edu.csus.recipedb

import edu.csus.recipedb.framework.Protocol
import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.services.MongoDatabase
import edu.csus.recipedb.services.RootController
import java.util.concurrent.Executors

fun main() {
    val server = WebServer.Builder(Protocol.HTTP, 8080)
        .executor(Executors.newCachedThreadPool())
        .services(RootController::class.java, MongoDatabase::class.java)
        .build()
    server.start()
}