package edu.csus.recipedb.framework.database

import java.sql.Connection

abstract class Database {

    private var connection: Connection? = null

    fun init(connection: Connection) {
        connection.autoCommit = false
        this.connection = connection
    }

    fun executePreparedStatement(statement: String, vararg objects: Any) {
        if (connection != null) {
            val stmt = connection!!.prepareStatement(statement)
            for (i in objects.indices) {
                val obj = objects[i]
                val index = i+1
                stmt.setObject(index, obj)
            }
            stmt.executeUpdate()
            stmt.close()
            connection!!.commit()
        }
    }

    fun query(statement: String) {
        if (connection != null) {
            val stmt = connection!!.createStatement()
        }
    }
}