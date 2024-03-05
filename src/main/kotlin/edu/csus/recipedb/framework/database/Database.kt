package edu.csus.recipedb.framework.database

import edu.csus.recipedb.framework.annotation.Instantiated
import edu.csus.recipedb.framework.services.Implementation

abstract class Database: Implementation() {

    @Instantiated
    private var driver: Driver? = null

    fun execute(statement: String) {
        if (driver != null)
            driver!!.execute(statement)
    }

    fun query(statement: String): Query? {
        if (driver != null)
            return driver!!.query(statement)
        return null
    }
}