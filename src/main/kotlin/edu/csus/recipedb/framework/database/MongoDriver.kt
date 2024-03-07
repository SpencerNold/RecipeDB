package edu.csus.recipedb.framework.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.Document
import java.util.logging.Level
import java.util.logging.Logger

class MongoDriver(url: String, username: String, password: String): Driver(url, username, password) {

    private var client: MongoClient? = null
    private var database: MongoDatabase? = null

    fun init() {
        // Disable the MongoDB driver logger
        val logger = Logger.getLogger("org.mongodb.driver")
        logger.level = Level.OFF

        val url = url.replace("<username>", username).replace("<password>", password)
        val api = ServerApi.builder().version(ServerApiVersion.V1).build()
        val settings = MongoClientSettings.builder().applyConnectionString(ConnectionString(url)).serverApi(api).build()
        client = MongoClients.create(settings)
    }

    fun setToAdmin() {
        database = client!!.getDatabase("admin")
    }

    fun setToLocal() {
        database = client!!.getDatabase("local")
    }

    fun isDatabaseSet(): Boolean {
        return database != null
    }

    fun execute(statement: String): String {
        if (!isDatabaseSet())
            setToLocal()
        return database!!.runCommand(Document.parse(statement)).toJson()
    }
}