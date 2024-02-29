package edu.csus.recipedb.framework.database

abstract class Driver(protected val url: String, protected val username: String, protected val password: String) {

    abstract fun init()
    abstract fun execute(statement: String)
    abstract fun query(statement: String)

    data class Factory(private val type: Type) {
        fun create(url: String, username: String, password: String): Driver {
            return when (type) {
                Type.MONGO -> MongoDriver(url, username, password)
            }
        }
    }

    enum class Type {
        MONGO
    }
}