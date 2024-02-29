package edu.csus.recipedb.framework.database

import edu.csus.recipedb.framework.services.Implementation

abstract class Database: Implementation() {

    private var driver: Driver? = null

    fun execute(statement: String) {
        if (driver != null)
            driver!!.execute(statement)
    }

    fun query(statement: String) {
        if (driver != null)
            driver!!.execute(statement)
    }
}