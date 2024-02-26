package edu.csus.recipedb.framework.database

import java.sql.Connection

abstract class Database {

    var connection: Connection? = null

    fun execute(statement: String) {
        if (connection != null) {
            val stmt = connection!!.createStatement()
            stmt.execute
        }
    }

    fun query(statement: String) {
        if (connection != null) {

        }
    }
}