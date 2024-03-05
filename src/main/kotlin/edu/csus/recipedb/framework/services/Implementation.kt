package edu.csus.recipedb.framework.services

import edu.csus.recipedb.framework.WebServer
import edu.csus.recipedb.framework.annotation.Instantiated

abstract class Implementation {

    @Instantiated
    private var server: WebServer? = null

    protected fun <T> getService(clazz: Class<T>): T {
        return server!!.service(clazz)!!
    }
}